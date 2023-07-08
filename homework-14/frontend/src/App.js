import './App.css';

import {
    LoginOutlined,
    LogoutOutlined,
    ManOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    SettingOutlined,
    ShopOutlined,
    UserAddOutlined,
    UserOutlined,
} from '@ant-design/icons';

import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";

import {Button, Layout, Menu, message, theme} from "antd";

import {NavLink, Route, Routes, useNavigate} from "react-router-dom";
import RegistrationPage from "./pages/Registration";
import LoginPage from "./pages/LoginPage";
import authService from "./services/auth.service";
import {logout} from "./slices/authSlice";
import {NotFoundPage} from "./pages/NotFoundPage";
import ProductsPage from "./pages/ProductPage";
import ClientPage from "./pages/ClientPage";
import {SettingProducts} from "./pages/SettingProductPage";

const {
    Header,
    Sider,
    Content
} = Layout;

const App = () => {
    const [collapsed, setCollapsed] = useState(false);

    const isLoginIn = useSelector((state) => state.auth.isLoggedIn);
    const user = useSelector((state) => state.auth.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    let isAdmin = false;

    if (isLoginIn && user.roles[0] === "ROLE_ADMIN") {
        isAdmin = true;
    }

    const {
        token: {colorBgContainer},
    } = theme.useToken();

    const handleLogOut = () => {
        dispatch(logout(user));
        authService.logout();
        isAdmin = false;

        const handleButtonClick = () => {
            setTimeout(() => {
                message.success('Ты успешно покинул тайное место!', 3);
            }, 100);
        };
        handleButtonClick();
        navigate("/")
    }

    return (
        <Layout className="App">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="demo-logo-vertical"/>

                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={['2']}
                    style={{
                        fontSize: '14px',
                    }}
                >
                    <Menu.Item key="0" icon={<ManOutlined/>} disabled>
                        Gachimuchi
                    </Menu.Item>

                    {isLoginIn ?
                        <Menu.Item key="1" icon={<UserOutlined/>}>
                            <NavLink to="/users" activeclassname="active">
                                Клиент
                            </NavLink>
                        </Menu.Item> :
                        ""}

                    <Menu.Item key="2" icon={<ShopOutlined/>}>
                        <NavLink to="/" activeclassname="active">
                            Каталог продуктов
                        </NavLink>
                    </Menu.Item>

                    {(isLoginIn && isAdmin) ?
                        <Menu.Item key="3" icon={<SettingOutlined/>}>
                            <NavLink to="/setting-products" activeclassname="active">
                                Настройка каталога
                            </NavLink>
                        </Menu.Item> :
                        ""}

                    {!isLoginIn ?
                        <Menu.Item key="4" icon={<LoginOutlined/>}>
                            <NavLink to="/login" activeclassname="active">
                                Войти
                            </NavLink>
                        </Menu.Item> :
                        ""}

                    {!isLoginIn ?
                        <Menu.Item key="5" icon={<UserAddOutlined/>}>
                            <NavLink to="/register" activeclassname="active">
                                Регистрация
                            </NavLink>
                        </Menu.Item> :
                        ""}

                    {isLoginIn ?
                        <Menu.Item key="5" icon={<LogoutOutlined/>}>
                            <NavLink to="/" activeclassname="active" onClick={handleLogOut}>
                                Выйти
                            </NavLink>
                        </Menu.Item> :
                        ""}

                </Menu>
            </Sider>
            <Layout>
                <Header
                    style={{
                        padding: 0,
                        background: colorBgContainer,
                    }}
                >
                    <Button
                        type="text"
                        icon={collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                        onClick={() => setCollapsed(!collapsed)}
                        style={{
                            fontSize: '32px',
                            width: 64,
                            height: 64,
                        }}/>
                </Header>
                <Content
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: '100vh',
                        background: colorBgContainer,
                    }}
                >
                    <Routes>
                        <Route index element={<ProductsPage/>}/>
                        <Route path="/users" element={<ClientPage/>}/>
                        <Route path="/setting-products" element={<SettingProducts/>}/>
                        <Route path="/login" element={<LoginPage/>}/>
                        <Route path="/register" element={<RegistrationPage/>}/>
                        <Route path="*" element={<NotFoundPage/>}/>
                    </Routes>
                </Content>
            </Layout>
        </Layout>
    )
};

export default App;