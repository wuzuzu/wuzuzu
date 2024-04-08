import {apiClient} from "../Client";

const startUrl = "/api/v1";

export const getMyRooms = async () => {
    return apiClient.get(startUrl + `/chat-rooms/my-rooms`);
};

export const getAllRooms = async () => {
    return apiClient.get(startUrl + `/chat-rooms`);
}