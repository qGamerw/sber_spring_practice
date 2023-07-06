import {Clients, Registration} from "../components/Client";

export const MainPage = () => {
    return (
        <>
            <div style={{float: "right", marginTop: -80, marginLeft: 20}}>
                <Registration/>
            </div>

            <Clients/>
        </>
    )
}