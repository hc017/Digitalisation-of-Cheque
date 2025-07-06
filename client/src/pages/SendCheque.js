import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useMutation, useQueryClient } from 'react-query';
import axios from 'axios';
import { useAuth } from '../contexts/AuthContext';
import { Send, Search, User, DollarSign, Calendar, MessageSquare } from 'lucide-react';
import LoadingSpinner from '../components/LoadingSpinner';
import toast from 'react-hot-toast';

const SendCheque = () => {
  const [formData, setFormData] = useState({
    receiverAccountNumber: '',
    amount: '',
    message: '',
    chequeDate: new Date().toISOString().split('T')[0]
  });
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [searching, setSearching] = useState(false);
  const [selectedReceiver, setSelectedReceiver] = useState(null);
  const [errors, setErrors] = useState({});

  const { user } = useAuth();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const createChequeMutation = useMutation(
    (chequeData) => axios.post('/cheques/create', chequeData),
    {
      onSuccess: () => {
        toast.success('Cheque sent successfully!');
        queryClient.invalidateQueries('dashboardStats');
        navigate('/sent-cheques');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to send cheque');
      }
    }
  );

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    
    // Clear errors when user starts typing
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const searchUsers = async (query) => {
    if (query.length < 3) {
      setSearchResults([]);
      return;
    }

    setSearching(true);
    try {
      const response = await axios.get(`/users/search?query=${encodeURIComponent(query)}`);
      setSearchResults(response.data);
    } catch (error) {
      console.error('Search error:', error);
      setSearchResults([]);
    } finally {
      setSearching(false);
    }
  };

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    searchUsers(query);
  };

  const selectReceiver = (receiver) => {
    setSelectedReceiver(receiver);
    setFormData(prev => ({ ...prev, receiverAccountNumber: receiver.accountNumber }));
    setSearchQuery(receiver.fullName);
    setSearchResults([]);
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.receiverAccountNumber) {
      newErrors.receiverAccountNumber = 'Please select a receiver';
    }

    if (!formData.amount) {
      newErrors.amount = 'Amount is required';
    } else if (parseFloat(formData.amount) <= 0) {
      newErrors.amount = 'Amount must be greater than 0';
    } else if (parseFloat(formData.amount) > parseFloat(user.balance)) {
      newErrors.amount = 'Insufficient balance';
    }

    if (!formData.chequeDate) {
      newErrors.chequeDate = 'Cheque date is required';
    } else if (new Date(formData.chequeDate) < new Date().setHours(0, 0, 0, 0)) {
      newErrors.chequeDate = 'Cheque date cannot be in the past';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    createChequeMutation.mutate(formData);
  };

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-banking-900">Send Digital Cheque</h1>
        <p className="text-banking-600">
          Create and send a secure digital cheque to another user
        </p>
      </div>

      {/* Current Balance */}
      <div className="banking-card bg-primary-50 border-primary-200">
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm text-primary-600">Available Balance</p>
            <p className="text-2xl font-bold text-primary-900">
              ${parseFloat(user?.balance || 0).toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
              })}
            </p>
          </div>
          <DollarSign className="w-8 h-8 text-primary-600" />
        </div>
      </div>

      {/* Cheque Form */}
      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="banking-card cheque-design">
          <div className="space-y-6">
            {/* Receiver Search */}
            <div>
              <label className="banking-label flex items-center">
                <User className="w-4 h-4 mr-2" />
                Receiver
              </label>
              <div className="relative">
                <div className="relative">
                  <input
                    type="text"
                    placeholder="Search by name, username, or account number"
                    value={searchQuery}
                    onChange={handleSearchChange}
                    className={`banking-input pl-10 ${errors.receiverAccountNumber ? 'border-red-500' : ''}`}
                  />
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-banking-400" />
                  {searching && (
                    <div className="absolute right-3 top-1/2 transform -translate-y-1/2">
                      <LoadingSpinner size="small" />
                    </div>
                  )}
                </div>
                
                {/* Search Results */}
                {searchResults.length > 0 && (
                  <div className="absolute z-10 w-full mt-1 bg-white border border-banking-200 rounded-lg shadow-banking max-h-60 overflow-y-auto">
                    {searchResults.map((user) => (
                      <button
                        key={user.id}
                        type="button"
                        onClick={() => selectReceiver(user)}
                        className="w-full px-4 py-3 text-left hover:bg-banking-50 border-b border-banking-100 last:border-b-0"
                      >
                        <div className="font-medium text-banking-900">{user.fullName}</div>
                        <div className="text-sm text-banking-600">@{user.username}</div>
                        <div className="text-xs text-banking-500">{user.accountNumber}</div>
                      </button>
                    ))}
                  </div>
                )}
              </div>
              {errors.receiverAccountNumber && (
                <p className="mt-1 text-sm text-red-600">{errors.receiverAccountNumber}</p>
              )}
              
              {/* Selected Receiver */}
              {selectedReceiver && (
                <div className="mt-3 p-3 bg-green-50 border border-green-200 rounded-lg">
                  <div className="flex items-center">
                    <div className="w-8 h-8 bg-green-100 rounded-full flex items-center justify-center">
                      <span className="text-green-600 font-semibold text-sm">
                        {selectedReceiver.fullName.charAt(0).toUpperCase()}
                      </span>
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-green-900">{selectedReceiver.fullName}</p>
                      <p className="text-xs text-green-600">{selectedReceiver.accountNumber}</p>
                    </div>
                  </div>
                </div>
              )}
            </div>

            {/* Amount */}
            <div>
              <label className="banking-label flex items-center">
                <DollarSign className="w-4 h-4 mr-2" />
                Amount
              </label>
              <input
                type="number"
                name="amount"
                step="0.01"
                min="0.01"
                placeholder="0.00"
                value={formData.amount}
                onChange={handleChange}
                className={`banking-input ${errors.amount ? 'border-red-500' : ''}`}
              />
              {errors.amount && (
                <p className="mt-1 text-sm text-red-600">{errors.amount}</p>
              )}
            </div>

            {/* Cheque Date */}
            <div>
              <label className="banking-label flex items-center">
                <Calendar className="w-4 h-4 mr-2" />
                Cheque Date
              </label>
              <input
                type="date"
                name="chequeDate"
                value={formData.chequeDate}
                onChange={handleChange}
                min={new Date().toISOString().split('T')[0]}
                className={`banking-input ${errors.chequeDate ? 'border-red-500' : ''}`}
              />
              {errors.chequeDate && (
                <p className="mt-1 text-sm text-red-600">{errors.chequeDate}</p>
              )}
              <p className="mt-1 text-xs text-banking-500">
                Cheque will be valid for 30 days from this date
              </p>
            </div>

            {/* Message */}
            <div>
              <label className="banking-label flex items-center">
                <MessageSquare className="w-4 h-4 mr-2" />
                Message (Optional)
              </label>
              <textarea
                name="message"
                rows={3}
                placeholder="Add a note for the receiver..."
                value={formData.message}
                onChange={handleChange}
                className="banking-input resize-none"
              />
            </div>
          </div>

          {/* Digital Signature Area */}
          <div className="mt-6 pt-6 border-t border-banking-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-banking-700">Digital Signature</p>
                <p className="text-xs text-banking-500">
                  Your private key will be used to sign this cheque
                </p>
              </div>
              <div className="text-right">
                <p className="text-sm font-medium text-banking-700">{user?.fullName}</p>
                <p className="text-xs text-banking-500">{user?.accountNumber}</p>
              </div>
            </div>
          </div>
        </div>

        {/* Submit Button */}
        <div className="flex space-x-4">
          <button
            type="submit"
            disabled={createChequeMutation.isLoading}
            className="banking-button-primary flex-1 flex items-center justify-center"
          >
            {createChequeMutation.isLoading ? (
              <LoadingSpinner size="small" />
            ) : (
              <>
                <Send className="w-4 h-4 mr-2" />
                Send Cheque
              </>
            )}
          </button>
          <button
            type="button"
            onClick={() => navigate('/dashboard')}
            className="banking-button-secondary px-6"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default SendCheque;