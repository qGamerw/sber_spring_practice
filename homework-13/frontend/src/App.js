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

const {Header, Sider, Content} = Layout;

const App = () => {
    const [collapsed, setCollapsed] = useState(false);
    const [size, setSize] = useState('large');

    const [isClient, setIsClient] = useState(true);
    const [isProduct, setIsProduct] = useState(false);
    const [isSetting, setIsSetting] = useState(false);

    const handleButtonClick = (key) => {
        if (parseInt(key) === 1) {
            setIsProduct(false);
            setIsSetting(false);
            setIsClient(true);
        } else if (parseInt(key) === 2) {
            setIsClient(false);
            setIsSetting(false);
            setIsProduct(true);
        } else if (parseInt(key) === 3) {
            setIsProduct(false);
            setIsClient(false);
            setIsSetting(true);
        }
    };

    const {
        token: {colorBgContainer},
    } = theme.useToken();
    return (
        <Layout className={"App"}>
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="demo-logo-vertical"/>
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    onClick={({key}) => handleButtonClick(key)}
                    style={{
                        fontSize: '14px',
                    }}
                    items={[
                        {
                            key: '0',
                            icon: <ManOutlined/>,
                            label: 'Gachimuchi',
                            disabled: true,
                        },
                        {
                            key: '1',
                            icon: <UserOutlined/>,
                            label: 'Клиент',

                        },
                        {
                            key: '2',
                            icon: <ShopOutlined/>,
                            label: 'Каталог продуктов',
                        },
                        {
                            key: '3',
                            icon: <SettingOutlined/>,
                            label: 'Настройка каталога',
                        },
                    ]}
                />
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
                        }}
                    />
                    {(isProduct) ? <SearchProduct/> : ""}
                    {(isSetting) ? <SearchProduct/> : ""}
                    <div style={{float : "right", margin: 10}}> {isClient ? <Registration/> : ""}</div>

                </Header>
                <Content
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: '100vh',
                        background: colorBgContainer,
                    }}
                >
                    {isClient ? <Clients/> : ""}
                    {isProduct ? <Products/> : ""}
                    {isSetting ? <SettingProducts/> : ""}
                </Content>
            </Layout>
        </Layout>
    );
};

export default App;