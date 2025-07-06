import React from 'react';
import { Link } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from 'axios';
import {
  Send,
  Inbox,
  FileText,
  TrendingUp,
  TrendingDown,
  Clock,
  CheckCircle,
  XCircle,
  AlertCircle
} from 'lucide-react';
import LoadingSpinner from '../components/LoadingSpinner';
import StatusBadge from '../components/StatusBadge';
import { format } from 'date-fns';

const Dashboard = () => {
  const { data: stats, isLoading } = useQuery(
    'dashboardStats',
    () => axios.get('/cheques/dashboard/stats').then(res => res.data),
    {
      refetchInterval: 30000, // Refetch every 30 seconds
    }
  );

  if (isLoading) {
    return <LoadingSpinner className="h-64" />;
  }

  const sentStatsMap = stats?.sentStats?.reduce((acc, stat) => {
    acc[stat.status] = { count: parseInt(stat.count), total: parseFloat(stat.total || 0) };
    return acc;
  }, {}) || {};

  const receivedStatsMap = stats?.receivedStats?.reduce((acc, stat) => {
    acc[stat.status] = { count: parseInt(stat.count), total: parseFloat(stat.total || 0) };
    return acc;
  }, {}) || {};

  const quickActions = [
    {
      name: 'Send Cheque',
      description: 'Create and send a new digital cheque',
      href: '/send-cheque',
      icon: Send,
      color: 'bg-blue-500',
    },
    {
      name: 'Received Cheques',
      description: 'View and manage received cheques',
      href: '/received-cheques',
      icon: Inbox,
      color: 'bg-green-500',
    },
    {
      name: 'Sent Cheques',
      description: 'Track your sent cheques',
      href: '/sent-cheques',
      icon: FileText,
      color: 'bg-purple-500',
    },
  ];

  const getStatusIcon = (status) => {
    switch (status) {
      case 'pending':
        return <Clock className="w-4 h-4 text-yellow-500" />;
      case 'accepted':
        return <CheckCircle className="w-4 h-4 text-green-500" />;
      case 'rejected':
        return <XCircle className="w-4 h-4 text-red-500" />;
      case 'cancelled':
        return <AlertCircle className="w-4 h-4 text-gray-500" />;
      case 'expired':
        return <AlertCircle className="w-4 h-4 text-orange-500" />;
      default:
        return null;
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-banking-900">Dashboard</h1>
        <p className="text-banking-600">
          Welcome back! Here's an overview of your digital cheque activity.
        </p>
      </div>

      {/* Quick Actions */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {quickActions.map((action) => {
          const Icon = action.icon;
          return (
            <Link
              key={action.name}
              to={action.href}
              className="banking-card hover:shadow-banking-lg transition-shadow duration-200 group"
            >
              <div className="flex items-center">
                <div className={`p-3 rounded-lg ${action.color} group-hover:scale-110 transition-transform duration-200`}>
                  <Icon className="w-6 h-6 text-white" />
                </div>
                <div className="ml-4">
                  <h3 className="text-lg font-semibold text-banking-900 group-hover:text-primary-600 transition-colors duration-200">
                    {action.name}
                  </h3>
                  <p className="text-sm text-banking-600">{action.description}</p>
                </div>
              </div>
            </Link>
          );
        })}
      </div>

      {/* Statistics */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Sent Cheques Stats */}
        <div className="banking-card">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-banking-900">Sent Cheques</h3>
            <TrendingUp className="w-5 h-5 text-blue-500" />
          </div>
          <div className="space-y-3">
            {['pending', 'accepted', 'rejected', 'cancelled'].map((status) => {
              const stat = sentStatsMap[status] || { count: 0, total: 0 };
              return (
                <div key={status} className="flex items-center justify-between">
                  <div className="flex items-center">
                    {getStatusIcon(status)}
                    <span className="ml-2 text-sm font-medium text-banking-700 capitalize">
                      {status}
                    </span>
                  </div>
                  <div className="text-right">
                    <div className="text-sm font-semibold text-banking-900">
                      {stat.count} cheques
                    </div>
                    <div className="text-xs text-banking-500">
                      ${stat.total.toLocaleString('en-US', { minimumFractionDigits: 2 })}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Received Cheques Stats */}
        <div className="banking-card">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-banking-900">Received Cheques</h3>
            <TrendingDown className="w-5 h-5 text-green-500" />
          </div>
          <div className="space-y-3">
            {['pending', 'accepted', 'rejected'].map((status) => {
              const stat = receivedStatsMap[status] || { count: 0, total: 0 };
              return (
                <div key={status} className="flex items-center justify-between">
                  <div className="flex items-center">
                    {getStatusIcon(status)}
                    <span className="ml-2 text-sm font-medium text-banking-700 capitalize">
                      {status}
                    </span>
                  </div>
                  <div className="text-right">
                    <div className="text-sm font-semibold text-banking-900">
                      {stat.count} cheques
                    </div>
                    <div className="text-xs text-banking-500">
                      ${stat.total.toLocaleString('en-US', { minimumFractionDigits: 2 })}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>

      {/* Recent Activity */}
      <div className="banking-card">
        <div className="flex items-center justify-between mb-6">
          <h3 className="text-lg font-semibold text-banking-900">Recent Activity</h3>
          <Link
            to="/transactions"
            className="text-sm text-primary-600 hover:text-primary-700 font-medium"
          >
            View all
          </Link>
        </div>
        
        {stats?.recentActivity?.length > 0 ? (
          <div className="space-y-4">
            {stats.recentActivity.map((cheque) => (
              <div key={cheque.id} className="flex items-center justify-between p-4 bg-banking-50 rounded-lg">
                <div className="flex items-center space-x-4">
                  <div className="flex-shrink-0">
                    {getStatusIcon(cheque.status)}
                  </div>
                  <div>
                    <p className="text-sm font-medium text-banking-900">
                      Cheque #{cheque.chequeNumber}
                    </p>
                    <p className="text-xs text-banking-600">
                      {cheque.Sender.fullName} → {cheque.Receiver.fullName}
                    </p>
                    <p className="text-xs text-banking-500">
                      {format(new Date(cheque.updatedAt), 'MMM dd, yyyy HH:mm')}
                    </p>
                  </div>
                </div>
                <div className="text-right">
                  <p className="text-sm font-semibold text-banking-900">
                    ${parseFloat(cheque.amount).toLocaleString('en-US', {
                      minimumFractionDigits: 2,
                      maximumFractionDigits: 2
                    })}
                  </p>
                  <StatusBadge status={cheque.status} />
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="text-center py-8">
            <FileText className="w-12 h-12 text-banking-300 mx-auto mb-4" />
            <p className="text-banking-500">No recent activity</p>
            <p className="text-sm text-banking-400">
              Start by sending or receiving your first digital cheque
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;