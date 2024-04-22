import React from "react";
import {IconButton} from '@mui/material';
import ChatIcon from '@mui/icons-material/Chat';
import ChattingApp from "./ChattingApp";

function ChattingAppButton() {

    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        if (localStorage.getItem('Authorization')) {
            setOpen(true);
        }
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div style={{
            position: 'fixed',
            bottom: '5%',
            right: '5%',
            boxShadow: '2px',
            backgroundColor: '#EFF2FB',
            borderRadius: '10px'
        }}>
            <IconButton color="primary" aria-label="open chat"
                        onClick={handleOpen}>
                <ChatIcon fontSize="large"/>
            </IconButton>
            {open === true ? (
                <ChattingApp open={open} handleClose={handleClose}/>
            ) : null
            }
        </div>
    );
}

export default ChattingAppButton;