import React, { createContext, useContext, useEffect, useState } from 'react';
import io from 'socket.io-client';
import { useAuth } from './AuthContext';
import toast from 'react-hot-toast';

const SocketContext = createContext();

export const useSocket = () => {
  const context = useContext(SocketContext);
  if (!context) {
    throw new Error('useSocket must be used within a SocketProvider');
  }
  return context;
};

export const SocketProvider = ({ children }) => {
  const [socket, setSocket] = useState(null);
  const [connected, setConnected] = useState(false);
  const { user } = useAuth();

  useEffect(() => {
    if (user) {
      const newSocket = io(process.env.REACT_APP_SOCKET_URL || 'http://localhost:5000');
      
      newSocket.on('connect', () => {
        setConnected(true);
        newSocket.emit('join', user.id);
      });

      newSocket.on('disconnect', () => {
        setConnected(false);
      });

      // Cheque notifications
      newSocket.on('newCheque', (data) => {
        toast.success(`New cheque received from ${data.senderName} - $${data.amount}`);
      });

      newSocket.on('chequeAccepted', (data) => {
        toast.success(`Your cheque to ${data.receiverName} has been accepted - $${data.amount}`);
      });

      newSocket.on('chequeRejected', (data) => {
        toast.error(`Your cheque to ${data.receiverName} has been rejected - $${data.amount}`);
      });

      newSocket.on('chequeCancelled', (data) => {
        toast.info(`A cheque from ${data.senderName} has been cancelled - $${data.amount}`);
      });

      setSocket(newSocket);

      return () => {
        newSocket.close();
      };
    } else {
      if (socket) {
        socket.close();
        setSocket(null);
        setConnected(false);
      }
    }
  }, [user]);

  const value = {
    socket,
    connected
  };

  return (
    <SocketContext.Provider value={value}>
      {children}
    </SocketContext.Provider>
  );
};