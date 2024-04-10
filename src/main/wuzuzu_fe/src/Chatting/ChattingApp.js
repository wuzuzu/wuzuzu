import React, {useEffect, useState} from "react";
import ChattingRoomInfoList from "./ChattingRoomInfoList";
import {Modal} from "@mui/joy";
import ChattingRoom from "./ChattingRoom";
import {chatRoomList, messageList} from "./ChatRoomDummyData";
import * as StompJs from "@stomp/stompjs";
import {getRoomMessages} from "../api/MessageApi";

export const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '20%',
    height: '80%',
    bgcolor: "#ADD8E6"
};

export const listStyle = {
    overflowY: 'scroll',
    mx: 'calc(-1 * var(--ModalDialog-padding))',
    px: 'var(--ModalDialog-padding)',
    mb: "50px"
}

export const listStyle_mt = {
    overflowY: 'scroll',
    mx: 'calc(-1 * var(--ModalDialog-padding))',
    px: 'var(--ModalDialog-padding)',
    mt: "50px",
    mb: "50px"
}

export const appStatusKey = {
    채팅방조회중: 1,
    채팅방생성중: 2,
    채팅방입장: 3
};

function ChattingApp({open, handleClose}) {

    const [stompClient, setStompClient] = useState(null);
    const [state, setState] = useState(appStatusKey.채팅방조회중);
    const [currentRoom, setCurrentRoom] = useState(null);
    const [roomMessages, setRoomMessages] = useState([]);
    const [receiveMessage, setReceiveMessage] = useState(null);

    const handleEnterClick = (room) => {
        if(room) {
            setCurrentRoom(room);
        }
    }

    useEffect(() => {
        if (currentRoom !== null && stompClient === null) {
            const client = new StompJs.Client({
                brokerURL: 'ws://localhost:8080/gs-guide-websocket',
                onConnect: async message => {
                    console.log(message);
                    client.subscribe(`/topic/chat-rooms/${currentRoom.chatRoomId}`,
                        onMessageReceived);
                    try {
                        const response = await getRoomMessages(currentRoom.chatRoomId);
                        setRoomMessages(response.data.data);
                        setState(appStatusKey.채팅방입장);
                    } catch (error) {
                        console.error("채팅 목록을 가져오는데 실패했습니다.", error);
                    }
                },
                onWebSocketError: error => {
                    console.error('Error with websocket', error);
                    setCurrentRoom(null);
                },
                onStompError: frame => {
                    console.error(
                        'Broker reported error: ' + frame.headers['message']);
                    console.error('Additional details: ' + frame.body);
                    setCurrentRoom(-1);
                },
            });

            client.activate();
            setStompClient(client);
        } else if (currentRoom === null && stompClient) {
            stompClient.deactivate();
            setStompClient(null);
        }
    }, [currentRoom]);

    const onMessageReceived = payload => {
        const message = JSON.parse(payload.body);
        setReceiveMessage(message);
    };

    const handleBackClick = () => {
        setState(appStatusKey.채팅방조회중);
        setCurrentRoom(null);
    }

    function switchUI() {
        switch (state) {
            case appStatusKey.채팅방조회중 :
                return <ChattingRoomInfoList
                    handleClose={handleClose}
                    handleEnterClick={handleEnterClick}
                />
            case appStatusKey.채팅방생성중 :
                return null;
            case appStatusKey.채팅방입장 :
                return <ChattingRoom
                    room={currentRoom}
                    onBackClick={handleBackClick}
                    stompClient={stompClient}
                    handleClose={handleClose}
                    receiveMessage={receiveMessage}
                    messages={roomMessages}
                />
        }
    }

    return (
        <Modal
            open={open}
            onClose={handleClose}
        >
            <div>
                {switchUI()}
            </div>
        </Modal>
    );
}

export default ChattingApp;