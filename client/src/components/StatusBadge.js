import React from 'react';

const StatusBadge = ({ status }) => {
  const getStatusClass = (status) => {
    switch (status?.toLowerCase()) {
      case 'pending':
        return 'status-pending';
      case 'accepted':
        return 'status-accepted';
      case 'rejected':
        return 'status-rejected';
      case 'cancelled':
        return 'status-cancelled';
      case 'expired':
        return 'status-expired';
      default:
        return 'status-badge bg-gray-100 text-gray-800';
    }
  };

  return (
    <span className={getStatusClass(status)}>
      {status?.charAt(0)?.toUpperCase() + status?.slice(1)}
    </span>
  );
};

export default StatusBadge;