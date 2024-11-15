import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import { useState } from "react";
import { QRCodeSVG } from "qrcode.react";
import "./GetTicketComponent.css";
import Service from "../model/Service";

export default function GetTicketComponent(props: {
  services: Service[];
  ticket: string;
  waitingTime: string;
  handleTicket: (service: string) => void;
  domain: string;
}) {
  const [serviceSelected, setServiceSelected] = useState("");
  const [showModal, setShowModal] = useState(false);

  const handleSelectService = (service: string) => {
    return () => {
      setServiceSelected(service);
    };
  };

  const handleGetTicket = (service: string) => {
    return () => {
      props.handleTicket(service);
      setShowModal(true);
    };
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setServiceSelected("");
  };

  return (
    <>
      <Container className="d-flex flex-column align-items-center justify-content-center full-height mt-3">
        <Row className="w-100">
          <Col>
            <h1 className="text-center w-100">Choose the service you need</h1>
          </Col>
        </Row>
        <Container className="services-container w-100 flex-grow-1 overflow-auto mt-3">
          <Row>
            {props.services.map((service, index) => (
              <Col key={index} xs={6} md={3} className="mb-3">
                <ServicesComponent
                  service={service.serviceName} // Assuming 'name' is a string property of 'Service'
                  onClick={handleSelectService(service.serviceId)}
                  isSelected={service.serviceId === serviceSelected}
                />
              </Col>
            ))}
          </Row>
        </Container>
        <Row className="w-100 d-flex justify-content-center mb-3 mt-3">
          <Col className="d-flex justify-content-center">
            <Button
              className="btn btn-primary btn-lg mb-3"
              onClick={handleGetTicket(serviceSelected)}
              disabled={!serviceSelected}
            >
              Get ticket
            </Button>
          </Col>
        </Row>
      </Container>

      <Modal
        show={showModal}
        onHide={handleCloseModal}
        backdrop="static"
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Header>
          <Modal.Title>Your ticket</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Your ticket number is:<strong> {props.ticket}</strong>
          <br />
          Estimated waiting time: <strong>{props.waitingTime}</strong>
          <br />
          QR code for ticket:
          <div className="mt-3 text-center">
            <QRCodeSVG
              value={props.domain + `/tickets/${props.ticket}`} // Update the URL every time you run ngrok
              size={128}
            />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={handleCloseModal}>
            Done
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

function ServicesComponent(props: {
  service: string;
  onClick: () => void;
  isSelected: boolean;
}) {
  return (
    <Button
      className={`service-button ${props.isSelected ? "selected" : ""}`}
      onClick={props.onClick}
      style={{
        width: "100%",
        height: "150px",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        padding: "10px",
        border: props.isSelected ? "2px solid #007bff" : "1px solid #ddd",
        borderRadius: "8px",
        cursor: "pointer",
        backgroundColor: props.isSelected ? "#e9f5ff" : "#fff",
        color: "black",
      }}
    >
      <span>{props.service}</span>
    </Button>
  );
}
