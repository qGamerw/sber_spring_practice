import "bootstrap/dist/css/bootstrap.css";
import { Navbar, Nav } from 'react-bootstrap';
export const NavbarHome = () => {
    return (
        <Navbar bg="info" expand="lg">
            <Navbar.Brand>Home</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Link>Account</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
};