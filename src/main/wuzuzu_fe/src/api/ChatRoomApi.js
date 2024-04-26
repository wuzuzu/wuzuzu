import {apiClient} from "./Client";

const startUrl = "/api/v1";

export const getMyRooms = async () => {
    return apiClient.get(startUrl + `/chat-rooms/my-rooms`);
};

export const getAllRooms = async () => {
    return apiClient.get(startUrl + `/chat-rooms`);
}

export const createChatRoom = async (chatRoom, image) => {
    const formData = new FormData();
    await formData.append('chatRoom',
        new Blob([JSON.stringify(chatRoom)], {type: 'application/json'}));

    if (image) {
        formData.append('image', image);
    }

    try {
        return await apiClient.post(startUrl + `/chat-rooms`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    } catch (error) {
        console.error('Error creating post:', error);
        throw error;
    }
}

export const enterChatRoom = async (chatRoomId) => {
    return apiClient.post(startUrl + `/chat-rooms/${chatRoomId}`);
}