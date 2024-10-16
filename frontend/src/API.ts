import Ticket from "./model/Ticket";
import Service from "./model/Service";

const BASEURL = 'http://localhost:8080/api';

const getAllServices = async (): Promise<Service[]> => {
    console.log("Getting all services");
    return await fetch(BASEURL + '/v1/tickets/allServices')
        .then(handleInvalidResponse)
        .then(response => response.json())
        .then(data => {
            return data.services.map((service: { serviceId: string, serviceName: string }) => new Service(service.serviceId, service.serviceName));
        });
};

const getQueueStatus = async () => {
    try {
        const response = await fetch(BASEURL + '/v1/services/queues/status');
        if(!response.ok) {
            throw new Error('Failed to fetch queue status');
        }
        const data = await response.json();
        return data;
    } catch(error) {
        console.error('Error fetching queue status:', error);
        return null;
    }
}

const getNextClientByCounterId = async (counterId: number) => {
    try{
        console.log("Counter ID: ", counterId);
        const response = await fetch(`${BASEURL}/v1/counters/${counterId}/callnext`);
        if(!response.ok) {
            throw new Error('Failed to fetch next client');
        }
        const data = await response.json();
        console.log(data);
        return data;
    } catch(error) {
        console.error('Error fetching next client:', error);
        return null;
    }
}

const getTicket = async (serviceId: string): Promise<Ticket> => {
    console.log("Service type: ", serviceId);
    return await fetch(BASEURL + '/v1/tickets/generate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ serviceId })
    })
    .then(handleInvalidResponse)
    .then(response => response.json())
    .then((data: { ticketCode: string, waitingTime: string }) => {
        return new Ticket(data.ticketCode, data.waitingTime);
    });
};

/*
const callNextClient = async (counterID: string): Promise<Ticket> => {
    return await fetch(BASEURL + '/customer/next', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ counterID })
    })
    .then(handleInvalidResponse)
    .then(response => response.json());
};
*/
// Helper function to handle invalid responses
function handleInvalidResponse(response: Response): Response {
    if (!response.ok) { 
        throw new Error(response.statusText); 
    }
    const type = response.headers.get('Content-Type');
    if (type !== null && type.indexOf('application/json') === -1) {
        throw new TypeError(`Expected JSON, got ${type}`);
    }
    return response;
}

// Export API object containing the methods
const API = {   
    getAllServices,
    getTicket,
    //callNextClient
    getQueueStatus,
    getNextClientByCounterId,
};

export default API;
