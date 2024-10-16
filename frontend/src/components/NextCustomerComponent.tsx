import React, { useEffect, useState } from 'react';
import { Button, Card, ListGroup, Spinner } from 'react-bootstrap';
import API from '../API';

const NextCustomerComponent = ({ currentTicket, setCurrentTicket }) => {
    const [queues, setQueues] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchQueuesLength = async () => {
        setLoading(true);
        const data = await API.getQueueStatus();
        setQueues(data);
        setLoading(false);
        console.log(data);
    };

    const handleCallNextCustomer = async () => {
        try {
            const nextCustomer = await API.getNextClientByCounterId(1);
            if (nextCustomer) {
                setCurrentTicket(nextCustomer);  // Update the current ticket
            } else {
                console.error('Failed to call next customer');
            }
        } catch (error) {
            console.error('Failed to call next customer:', error);
        }
    };

    useEffect(() => {
        fetchQueuesLength();
    }, [currentTicket]);

    return (
        <div className="container mt-4">
            <Card className="text-center" style={{ border: '1px solid #007bff', borderRadius: '10px' }}>
                <Card.Body>
                    <Card.Title className="mb-4" style={{ color: '#007bff' }}>Officer display</Card.Title>
                    {loading ? (
                        <Spinner animation="border" variant="primary" />
                    ) : (
                        <div>
                            <Card.Text className="mb-4" style={{ fontSize: '1.2rem' }}>
                                <strong>Current Ticket:</strong> {currentTicket ? currentTicket.ticketId : 'None'}
                            </Card.Text>
                            <Card.Subtitle className="mb-3" style={{ fontWeight: 'bold', fontSize: '1.1rem' }}>Queue Lengths:</Card.Subtitle>
                            <ListGroup className="mb-4" style={{ maxWidth: '300px', margin: '0 auto' }}> {/* Limita la larghezza e centra */}
                                {queues.map((queue) => (
                                    <ListGroup.Item key={queue.serviceName} style={{ fontSize: '1.1rem' }}>
                                        {queue.serviceName} - Length: {queue.queueLength}
                                    </ListGroup.Item>
                                ))}
                            </ListGroup>
                            <Button variant="primary" onClick={handleCallNextCustomer}>
                                Call Next Customer
                            </Button>
                        </div>
                    )}
                </Card.Body>
            </Card>
        </div>
    );
};

export default NextCustomerComponent;



