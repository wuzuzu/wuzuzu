import React from 'react';
import SignUp from './views/SignUp';
import Login from "./views/Login";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Main from "./views/Main";
import Mypage from "./views/Mypage";
import Spot from "./views/Spot";
import Favorite from "./views/Favorite";
import SpotDetail from "./views/SpotDetail";
import ChattingAppButton from "./Chatting/ChattingAppButton";
import CommunityMain from "./Community/CommunityMain";
import OrderMain from "./Order/OrderMain";
import Layout from "./views/Layout";
import {createTheme, ThemeProvider} from "@mui/material/styles";

const globalTheme = createTheme({
    typography: {
        fontFamily: 'Jua-Regular',
    },
});

function App() {
    return (
        <ThemeProvider theme={globalTheme}>
            <div>
                <BrowserRouter>

                    <Routes>
                        <Route exact path="/" element={<Login/>}/>
                        <Route path="/signup" element={<SignUp/>}/>
                        <Route path="/Main" element={<Main/>}/>
                        <Route path="/Mypage"
                               element={<Layout><Mypage/></Layout>}/>
                        <Route path="/Spot" element={<Layout><Spot/></Layout>}/>
                        <Route path="/Favorite"
                               element={<Layout><Favorite/></Layout>}/>
                        <Route path="/SpotDetail"
                               element={<Layout><SpotDetail/></Layout>}/>
                        <Route path="/community"
                               element={<Layout><CommunityMain/></Layout>}/>
                        <Route path="/transaction"
                               element={<Layout><OrderMain/></Layout>}/>
                    </Routes>
                </BrowserRouter>
                <ChattingAppButton/>
            </div>
        </ThemeProvider>
    );
}

export default App;
