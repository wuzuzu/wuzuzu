import React, {useState, useEffect} from "react";
import {Box, IconButton, MenuItem, Modal, Select} from '@mui/material';
import ChatIcon from '@mui/icons-material/Chat';
import ChattingRoomList from "./ChattingRoomList";
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
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

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
                        // onChange={handleChange}
                        sx={{width: "100%"}}
                    >
                        <MenuItem value={"my"}>내 채팅</MenuItem>
                        <MenuItem value={"all"}>모든 채팅</MenuItem>
                    </Select>
                    <ChattingRoomList/>
                </Box>
            </Modal>
        </div>
    );
}

export default ChattingApp;