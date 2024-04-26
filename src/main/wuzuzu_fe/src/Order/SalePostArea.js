import {
    Box,
    Button,
    Grid,
    IconButton,
    Pagination,
    TextField
} from "@mui/material";
import React, {useEffect, useState} from "react";
import SalePost from "./SalePost";
import {getPosts, searchPosts} from "../api/SalePostApi";
import {Refresh as RefreshIcon} from "@mui/icons-material";
import SearchIcon from "@mui/icons-material/Search";

export const category = [
    {
        value: null,
        name: '전체',
    },
    {
        value: "사료",
        name: '사료',
    },
    {
        value: "장난감",
        name: '장난감',
    },
    {
        value: "용품",
        name: '용품',
    },
    {
        value: "간식",
        name: '간식',
    },
    {
        value: "의류",
        name: '의류',
    },
];

function SalePostArea({
    handleCreateClick,
    handlePostClick,
    handleOrderHistoryClick
}) {
    const [postList, setPostList] = useState();
    const [searchParams, setSearchParams] = useState({});
    const [keyword, setKeyword] = useState("");
    const [totalPage, setTotalPage] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);

    function onClickSearch() {
        setSearchParams((prevParams) => ({
            ...prevParams,
            keyword: keyword,
        }));
    }

    const handleKeywordChange = (value) => {
        setKeyword(value);
    }

    const handlePageChange = (event, value) => {
        if (currentPage !== value) {
            setCurrentPage(value);
            setSearchParams((prevParams) => ({
                ...prevParams,
                page: value,
            }));
        }
    };

    useEffect(() => {
        if (keyword) {
            searchPosts(searchParams)
            .then((response) => {
                const data = response.data;
                setPostList(data.responseList);
                setTotalPage(data.pagingUtil.totalPages);
            })
            .catch((error) => {
                // 에러 처리
                console.error(error);
            });
        } else {
            fetchPosts()
        }

    }, [searchParams]);

    // useEffect(() => {
    //   fetchPosts();
    // }, []);

    async function fetchPosts() {
        try {
            const response = await getPosts();
            setPostList(response.data.data);
        } catch (e) {
            alert("상품 리스트 조회 실패...")
        }
    }

    const handleRefreshClick = () => {
        fetchPosts();
    };

    return (
        <div>
            <Box sx={{
                m: 2,
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                flexGrow: 1
            }}>

                <IconButton
                    color="primary"
                    onClick={handleRefreshClick}
                >
                    <RefreshIcon/>
                </IconButton>
                <form
                    onSubmit={(e) => {
                        e.preventDefault();
                        onClickSearch();
                    }}
                >
                    <TextField
                        label="키워드 검색"
                        variant="standard"
                        sx={{flexGrow: 1, ml: 2}}
                        value={keyword}
                        onChange={(e) => {
                            handleKeywordChange(e.target.value)
                        }}
                    />
                    <IconButton
                        type="submit"
                        aria-label="search"
                        sx={{width: '50px', height: '50px', mr: 2}}
                    >
                        <SearchIcon/>
                    </IconButton>
                </form>


                <Box sx={{display: 'flex', justifyContent: 'flex-end'}}>
                    <Button variant="contained"
                            onClick={handleOrderHistoryClick}
                            sx={{mr: 2}}>
                        내 주문 조회
                    </Button>
                    <Button variant="contained" onClick={handleCreateClick}>
                        상품 등록하기
                    </Button>
                </Box>
            </Box>
            <Grid container spacing={2}>
                {postList && postList.map((post) => {
                    return (
                        <SalePost
                            key={post.salePostId}
                            post={post}
                            handlePostClick={handlePostClick}
                        />
                    )
                })}
            </Grid>
            <Box sx={{display: 'flex', justifyContent: 'center', mt: 4}}>
                <Pagination count={totalPage} page={currentPage}
                            onChange={handlePageChange}/>
            </Box>
        </div>
    );
}

export default SalePostArea;