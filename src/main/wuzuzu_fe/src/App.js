import logo from './logo.svg';
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
export default App;
