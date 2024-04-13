import {apiClient} from "../Client";

const startUrl = "/api/v1";

export const searchPosts = async (searchParams) => {
    const {
        page,
        pageSize,
        column,
        title,
        contents,
        keyword,
        categoryName,
    } = searchParams;

    const params = {};

    if (page) {
        params.page = page;
    }
    if (pageSize) {
        params.pageSize = pageSize;
    }
    if (column) {
        params.column = column;
    }
    if (title) {
        params.title = title;
    }
    if (contents) {
        params.contents = contents;
    }
    if (keyword) {
        params.keyword = keyword;
    }
    if (categoryName) {
        params.categoryName = categoryName;
    }

    return apiClient.get(startUrl + `/communityposts/search`, {params});
};

export const createCommunityPost = async (communityPost) => {
    return apiClient.post(startUrl + `/communityposts`, communityPost);
}

export const uploadImage = async (communityPostId, image) => {
    const formData = new FormData();
    formData.append('image', image);

    try {
        const response = await apiClient.post(
            startUrl + `/communityposts/${communityPostId}/multipart-files`,
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