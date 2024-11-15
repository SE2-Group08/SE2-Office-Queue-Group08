// import { useState } from 'react'
import "./App.css";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import NotFoundComponent from "./components/NotFoundComponent";
import GetTicketComponent from "./components/GetTicketComponent";
import CallCustomerComponent from "./components/CallCustomerComponent";
import NextCustomerComponent from "./components/NextCustomerComponent";
import TicketComponent from "./components/TicketComponent";
import { useState } from "react";
import { useEffect } from "react";
import Service from "./model/Service";
import API from "./API";

const domain = "https://fowl-light-macaque.ngrok-free.app";

function Home() {
  return (
    <div className="d-flex flex-column justify-content-center align-items-center vh-100">
      <div className="mb-4">
        <Link to="/get-ticket" className="btn btn-primary btn-lg m-2">
          Get a ticket
        </Link>
        <Link to="/call-customer" className="btn btn-secondary btn-lg m-2">
          Main display
        </Link>
        <Link to="/next-customer" className="btn btn-success btn-lg m-2">
          Officer display
        </Link>
      </div>
    </div>
  );
}

function App() {
  const [ticket, setTicket] = useState("");
  const [waitingTime, setWaitingTime] = useState("");
  const [currentTicket, setCurrentTicket] = useState(null);
  const [services, setServices] = useState<Service[]>([]);

  useEffect(() => {
    console.log("Current Ticket in App:", currentTicket);
  }, [currentTicket]);

  useEffect(() => {
    API.getAllServices().then((services) =>
      setServices(
        services.map(
          (service) => new Service(service.serviceId, service.serviceName)
        )
      )
    );
  }, []);

  const handleTicket = async (serviceId: string) => {
    try {
      const ticketData = await API.getTicket(serviceId);
      setTicket(String(ticketData.ticketId));
      setWaitingTime(String(ticketData.waitingTime));
    } catch (error) {
      console.error("Failed to get ticket:", error);
    }
  };

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route
        path="/get-ticket"
        element={
          <GetTicketComponent
            services={services}
            ticket={ticket}
            waitingTime={waitingTime}
            handleTicket={handleTicket}
            domain={domain}
          />
        }
      />
      <Route
        path="/call-customer"
        element={<CallCustomerComponent currentTicket={currentTicket} />}
      />
      <Route
        path="/next-customer"
        element={
          <NextCustomerComponent
            currentTicket={currentTicket}
            setCurrentTicket={setCurrentTicket}
          />
        }
      />
      <Route path="/tickets/:ticketId" element={<TicketComponent />} />
      <Route path="*" element={<NotFoundComponent />} />
    </Routes>
  );
}

export default App;
