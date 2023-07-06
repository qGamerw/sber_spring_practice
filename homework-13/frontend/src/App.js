import './App.css';

import {
    ManOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    SettingOutlined,
    ShopOutlined,
    UserOutlined
} from '@ant-design/icons';
import {Clients, Registration} from "./components/Client";
import SearchProduct from "./search/SearchProduct";

import {Button, Layout, Menu, theme} from 'antd';
import {useState} from 'react';
import {Products} from "./components/Product";
import {SettingProducts} from "./components/Setting";
import {MainPage} from "./pages/Main";
import {NotFoundPage} from "./pages/NotFoundPage";
import {Link, NavLink, Route, Routes} from "react-router-dom";
import {ProductsPage} from "./pages/ProductsPage";
import {SettingProductsPage} from "./pages/SettingProductPage";
import RegistrationPage from "./pages/RegistrationPage";
import LoginPage from "./pages/LoginPage";
import {useDispatch, useSelector} from "react-redux";

const {Header, Sider, Content} = Layout;

const App = () => {
    const [collapsed, setCollapsed] = useState(false);

    const {user: currentUser} = useSelector((state) => state.auth);
    const dispatch = useDispatch();
    console.log(currentUser)
    const handleButtonClick = (key) => {

        console.log(key)
        if (parseInt(key) === 1) {

            return <Link to="/"></Link>
        } else if (parseInt(key) === 2) {

            return <Link to="/products"></Link>
        }
    };

    const {
        token: {colorBgContainer},
    } = theme.useToken();
    return (
        <Layout className="App">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="demo-logo-vertical" />
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    style={{
                        fontSize: '14px',
                    }}
                >
                    <Menu.Item key="0" icon={<ManOutlined />} disabled>
                        Gachimuchi
                    </Menu.Item>

                    <Menu.Item key="1" icon={<UserOutlined />}>
                        <NavLink to="/" activeclassname="active">
                            Клиент
                        </NavLink>
                    </Menu.Item>

                    <Menu.Item key="2" icon={<ShopOutlined />}>
                        <NavLink to="/products" activeclassname="active">
                            Каталог продуктов
                        </NavLink>
                    </Menu.Item>

                    <Menu.Item key="3" icon={<SettingOutlined />}>
                        <NavLink to="/setting-products" activeclassname="active">
                            Настройка каталога
                        </NavLink>
                    </Menu.Item>

                    <Menu.Item key="4" icon={<SettingOutlined />}>
                        <NavLink to="/login" activeclassname="active">
                            Вход
                        </NavLink>
                    </Menu.Item>

                    <Menu.Item key="5" icon={<SettingOutlined />}>
                        <NavLink to="/register" activeclassname="active">
                            Регистрация
                        </NavLink>
                    </Menu.Item>

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
                        icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
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
                        <Route index element={<MainPage/>}/>
                        <Route path="/products" element={<ProductsPage />} />
                        <Route path="/setting-products" element={<SettingProductsPage/>}/>
                        <Route path="*" element={<NotFoundPage/>}/>
                        <Route path="/login" element={<LoginPage/>}/>
                        <Route path="/register" element={<RegistrationPage/>}/>
                    </Routes>
                </Content>
            </Layout>
        </Layout>
    )
};

export default App;