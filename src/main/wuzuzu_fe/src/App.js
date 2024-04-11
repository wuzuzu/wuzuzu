import React from 'react';
import SignUp from './views/SignUp';
import Login from "./views/Longin";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Main from "./views/Main";
import Mypage from "./views/Mypage";
import Spot from "./views/Spot";
import ChattingAppButton from "./Chatting/ChattingAppButton";


function App() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                  <Route exact path="/" element={<Login/>}/>
                  <Route path="/signup" element={<SignUp/>}/>
                  <Route path="/Main" element={<Main/>}/>
                  <Route path="/Mypage" element={<Mypage/>}/>
                  <Route path="/Spot" element={<Spot/>}/>
                </Routes>
            </BrowserRouter>
            <ChattingAppButton/>
        </div>
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
