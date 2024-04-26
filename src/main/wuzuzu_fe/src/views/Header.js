import * as React from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {Link, useNavigate} from "react-router-dom";

function Header() {

    const sections = [
        {title: '🐶스팟 조회', url: '/Spot'},
        {title: '🐹 용품 거래', url: '/transaction'},
        {title: '🐱 커뮤니티', url: '/community'}
    ];

    const title = 'WuZuZu'

    const navigate = useNavigate();

    //마이페이지로 이동
    const handleMypageClick = () => {
        navigate('/mypage') // v6
    };

    // 로그 아웃
    const handleLogout = async () => {
        try {
            // Call your API here to perform the login
            const response = await fetch('/api/v1/users/logout', {
                method: 'POST',
                headers: {
                    'Authorization': localStorage.getItem('Authorization'),
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                console.error('성공');
                navigate("/");
                // Handle successful login (e.g., set user state, redirect, etc.)
            } else {
                console.error('no');
            }
        } catch (error) {
            console.error('Error logging in:', error);

        }
    };

    return (
        <React.Fragment>
            <Toolbar sx={{
                borderBottom: 1,
                borderColor: 'divider',
                overflowX: 'auto'
            }}>
                <Button onClick={handleLogout} size="small" style={{
                    backgroundColor: '#FBEFEF',
                    color: '#6E6E6E',
                    borderColor: '#6E6E6E'
                }}>
                    log out
                </Button>

                <Typography
                    component="h2"
                    variant="h5"
                    color="inherit"
                    align="center"
                    noWrap
                    sx={{
                        flex: 1,
                        cursor: 'pointer'
                    }} // 커서를 포인터로 변경하여 클릭 가능하도록 설정
                    onClick={() => navigate('/Main')} // 클릭 이벤트 추가

                >
                    {title}
                </Typography>


                <Button onClick={handleMypageClick} size="small" style={{
                    backgroundColor: '#FBEFEF',
                    color: '#6E6E6E',
                    borderColor: '#6E6E6E'
                }}>
                    My page
                </Button>


            </Toolbar>
            <Toolbar
                component="nav"
                variant="dense"
                sx={{
                    borderBottom: 1,
                    borderColor: 'divider',
                    justifyContent: 'space-between',
                    overflowX: 'auto'
                }}
            >
                {sections.map((section) => (
                    <Link
                        color="inherit"
                        noWrap
                        key={section.title}
                        variant="body2"
                        to={section.url}
                        sx={{p: 1, flexShrink: 0}}
                    >
                        {section.title}
                    </Link>
                ))}
            </Toolbar>
        </React.Fragment>
    );
}

Header.propTypes = {
    sections: PropTypes.arrayOf(
        PropTypes.shape({
            title: PropTypes.string.isRequired,
            url: PropTypes.string.isRequired,
        }),
    ).isRequired,
    title: PropTypes.string.isRequired,
};

export default Header;