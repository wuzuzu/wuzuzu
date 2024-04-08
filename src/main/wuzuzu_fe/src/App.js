import logo from './logo.svg';
import React from 'react';
import SignUp from './components/SignUp';
import Login from "./components/Longin";
import {
  BrowserRouter,
  BrowserRouter as Router,
  Route, Routes,
  Switch
} from 'react-router-dom';
import Main from "./components/Main";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route exact path="/" element={<Login/>} />
          <Route path="/signup" element={<SignUp/>} />
          <Route path="/Main" element={<Main/>} />
        </Routes>
      </BrowserRouter>
  );
}

// import './App.css';
// import React, {useState, useEffect} from 'react';
// function App() {
//   const [message, setMessage]=useState([]);
//   useEffect(()=>{
//     fetch("/api/v1/favorites/demo-web")
//     .then((response)=>{
//       return response.json();
//     })
//     .then((data)=>{
//       setMessage(data);
//     });
//   },[]);
//   return (
//       <div>
//         {message}
//       </div>
//   );
// }

export default App;
