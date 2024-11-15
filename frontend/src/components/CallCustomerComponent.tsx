import React, { useEffect, useState } from 'react';
import { Card, Table, ListGroup, Spinner } from 'react-bootstrap';
import API from '../API';  // Assumendo che esista un'API per ottenere la lunghezza delle code

const CallCustomer = ({ currentTicket }) => {
  const [counters, setCounters] = useState([
    { counterId: 1, serviceName: null, ticketId: null },
    { counterId: 2, serviceName: null, ticketId: null },
    { counterId: 3, serviceName: null, ticketId: null },
  ]);
  const [queues, setQueues] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Funzione per ottenere la lunghezza delle code
    const fetchQueuesLength = async () => {
      try {
        setLoading(true);
        const data = await API.getQueueStatus();
        setQueues(data);
        setLoading(false);
      } catch (error) {
        console.error('Failed to fetch queues:', error);
        setLoading(false);
      }
    };

    fetchQueuesLength();
  }, []);

  useEffect(() => {
    if (currentTicket) {
      // Aggiorna il counter corrispondente al ticket corrente
      setCounters((prevCounters) =>
        prevCounters.map((counter) =>
          counter.counterId === currentTicket.counterId
            ? {
                ...counter,
                serviceName: currentTicket.serviceName,
                ticketId: currentTicket.ticketId,
              }
            : counter
        )
      );
    }
  }, [currentTicket]);

  return (
    <div className="container mt-4">
      <Card className="text-center" style={{ border: '1px solid #007bff', borderRadius: '10px' }}>
        <Card.Body>
          <Card.Title className="mb-4" style={{ color: '#007bff' }}>
            Main display
          </Card.Title>
          <div className="table-responsive">
            <Table striped bordered hover className="mx-auto" style={{ maxWidth: '600px' }}>
              <thead>
                <tr>
                  <th>Counter ID</th>
                  <th>Service Name</th>
                  <th>Ticket ID</th>
                </tr>
              </thead>
              <tbody>
                {counters.map((counter) => (
                  <tr key={counter.counterId}>
                    <td className="text-center">{counter.counterId}</td>
                    <td className="text-center">{counter.serviceName || 'No service yet'}</td>
                    <td className="text-center">{counter.ticketId || 'No ticket yet'}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>

          {/* Sezione per la lunghezza delle code */}
          <Card.Subtitle className="mt-4 mb-2" style={{ fontWeight: 'bold', fontSize: '1.1rem' }}>
            Queue Lengths
          </Card.Subtitle>
          {loading ? (
            <Spinner animation="border" variant="primary" />
          ) : (
            <ListGroup className="mb-4" style={{ maxWidth: '500px', margin: '0 auto' }}>
              {queues.length > 0 ? (
                queues.map((queue) => (
                  <ListGroup.Item key={queue.serviceName} style={{ fontSize: '1.1rem' }}>
                    {queue.serviceName} - Length: {queue.queueLength}
                  </ListGroup.Item>
                ))
              ) : (
                <ListGroup.Item style={{ fontSize: '1.1rem' }}>
                  No queues available
                </ListGroup.Item>
              )}
            </ListGroup>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default CallCustomer;
