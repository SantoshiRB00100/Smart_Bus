import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import Navbar from "./component/Navbar";
import Footer from "./component/Footer";
import ProtectedRoute from "./protected/ProtectedRoute";

import Home from "./pages/Home";
import Services from "./pages/Services";
import TrackBus from "./pages/TrackBus";
import About from "./pages/About";
import Contact from "./pages/Contact";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Buses from "./pages/Buses";

const App = () => (
  <Router>
    <Navbar />
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<Home />} /> {/* HOME is now public */}
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />

      {/* Protected Routes */}
      <Route
        path="/services"
        element={
          <ProtectedRoute allowedRoles={["Student","Teacher","Driver","Employee","Public Passenger","Admin"]}>
            <Services />
          </ProtectedRoute>
        }
      />
      <Route
        path="/about"
        element={
          <ProtectedRoute allowedRoles={["Student","Teacher","Driver","Employee","Public Passenger","Admin"]}>
            <About />
          </ProtectedRoute>
        }
      />
      <Route
        path="/contact"
        element={
          <ProtectedRoute allowedRoles={["Student","Teacher","Driver","Employee","Public Passenger","Admin"]}>
            <Contact />
          </ProtectedRoute>
        }
      />
      <Route
        path="/track"
        element={
          <ProtectedRoute allowedRoles={["Student","Teacher","Driver","Employee","Public Passenger","Admin"]}>
            <TrackBus />
          </ProtectedRoute>
        }
      />
      <Route
        path="/buses"
        element={
          <ProtectedRoute allowedRoles={["Admin"]}>
            <Buses />
          </ProtectedRoute>
        }
      />

      <Route path="/unauthorized" element={<h1 className="p-8 text-center">❌ Unauthorized</h1>} />

      {/* Catch-all */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
    <Footer />
  </Router>
);

export default App;













