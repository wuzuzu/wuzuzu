import React, {useEffect, useState} from "react";
import {Box, IconButton, MenuItem, Modal, Select} from '@mui/material';
import ChatIcon from '@mui/icons-material/Chat';
import ChattingRoomList from "./ChattingRoomList";
import {getAllRooms, getMyRooms} from "./api/ChatRoomApi";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '20%',
    height: '80%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    borderRadius: '10px',
    boxShadow: 24,
    overflow: 'hidden',
};

function ChattingApp() {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => {
        setOpen(true)
    };
    const handleClose = () => {
        setOpen(false)
    };

    const [roomList, setRoomList] = useState([]);
    const [menuItem, setMenuItem] = useState("my");

    // async/await 사용 예시
    async function getMyRoomList() {
        try {
            const response = await getMyRooms();
            setRoomList(response.data);
            // 성공 처리
        } catch (error) {
            console.error("채팅방 목록을 가져오는데 실패했습니다.", error);
            // 사용자에게 에러 메시지 표시 또는 필요한 조치 수행
        }
    }

    async function getRoomList() {
        try {
            const response = await getAllRooms();
            setRoomList(response.data);
            // 성공 처리
        } catch (error) {
            console.error("채팅방 목록을 가져오는데 실패했습니다.", error);
            // 사용자에게 에러 메시지 표시 또는 필요한 조치 수행
        }
    }

    const handleChange = (event) => {
        setMenuItem(event.target.value);
    }

    useEffect(() => {
        if (menuItem === "my") {
            getMyRoomList();
        } else if (menuItem === "all") {
            getRoomList();
        }
    }, [menuItem]);

    return (
        <div style={{position: 'fixed', bottom: '5%', right: '5%'}}>
            <IconButton color="primary" aria-label="open chat"
                        onClick={handleOpen}>
                <ChatIcon fontSize="large"/>
            </IconButton>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Select
                        labelId="demo-simple-select-filled-label"
                        id="demo-simple-select-filled"
                        defaultValue={"my"}
                        onChange={handleChange}
                        sx={{width: "100%"}}
                    >
                        <MenuItem value={"my"}>내 채팅</MenuItem>
                        <MenuItem value={"all"}>모든 채팅</MenuItem>
                    </Select>
                    <ChattingRoomList props={roomList}/>
                </Box>
            </Modal>
        </div>
    );
}

export default ChattingApp;