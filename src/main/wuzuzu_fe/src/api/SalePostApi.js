import {apiClient} from "../Client";

const startUrl = "/api/v1/sale-posts";

export const getPostDetail = async (salePostId) => {
    return apiClient.get(startUrl + `/${salePostId}`);
}

export const getPosts = async () => {
    return apiClient.get(startUrl);
}

export const createSalePost = async (salePost) => {
    return apiClient.post(startUrl, salePost);
}

export const uploadImage = async (salePostId, image) => {
    const formData = new FormData();
    formData.append('image', image);

    try {
        const response = await apiClient.post(
            startUrl + `/${salePostId}/multipart-files`,
            formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            }
        );
        return response;
    } catch (error) {
        console.error('Error uploading image:', error);
        throw error;
    }
};