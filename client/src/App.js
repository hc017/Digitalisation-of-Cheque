import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { SocketProvider } from './contexts/SocketContext';

// Components
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import AdminRoute from './components/AdminRoute';

// Pages
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import SendCheque from './pages/SendCheque';
// import ReceivedCheques from './pages/ReceivedCheques';
// import SentCheques from './pages/SentCheques';
// import ChequeDetails from './pages/ChequeDetails';
// import TransactionHistory from './pages/TransactionHistory';
// import Profile from './pages/Profile';

// // Admin Pages
// import AdminDashboard from './pages/admin/AdminDashboard';
// import AdminUsers from './pages/admin/AdminUsers';
// import AdminCheques from './pages/admin/AdminCheques';
// import AdminTransactions from './pages/admin/AdminTransactions';
// import AdminLogs from './pages/admin/AdminLogs';

function AppRoutes() {
  const { user } = useAuth();

  return (
    <Routes>
      {/* Public Routes */}
      <Route 
        path="/login" 
        element={!user ? <Login /> : <Navigate to="/dashboard" replace />} 
      />
      <Route 
        path="/register" 
        element={!user ? <Register /> : <Navigate to="/dashboard" replace />} 
      />
      
      {/* Protected Routes */}
      <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="send-cheque" element={<SendCheque />} />
        {/* <Route path="received-cheques" element={<ReceivedCheques />} />
        <Route path="sent-cheques" element={<SentCheques />} />
        <Route path="cheque/:id" element={<ChequeDetails />} />
        <Route path="transactions" element={<TransactionHistory />} />
        <Route path="profile" element={<Profile />} /> */}
        
        {/* Admin Routes
        <Route path="admin" element={<AdminRoute />}>
          <Route index element={<Navigate to="/admin/dashboard" replace />} />
          <Route path="dashboard" element={<AdminDashboard />} />
          <Route path="users" element={<AdminUsers />} />
          <Route path="cheques" element={<AdminCheques />} />
          <Route path="transactions" element={<AdminTransactions />} />
          <Route path="logs" element={<AdminLogs />} />
        </Route>
      } */}
      </Route>
      
      {/* Catch all */}
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}

function App() {
  return (
    <AuthProvider>
      <SocketProvider>
        <div className="min-h-screen bg-banking-50">
          <AppRoutes />
        </div>
      </SocketProvider>
    </AuthProvider>
  );
}

export default App;