import React from 'react';
// import SignUp from './components/SignUp';
// import Login from "./components/Longin";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import ChattingAppButton from "./Chatting/ChattingAppButton";
import LogInForm from "./LogInForm";

// import Main from "./components/Main";

function App() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route exact path="/" element={<LogInForm/>}/>
                    {/*<Route path="/signup" element={<SignUp/>}/>*/}
                    {/*<Route path="/Main" element={<Main/>}/>*/}
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
