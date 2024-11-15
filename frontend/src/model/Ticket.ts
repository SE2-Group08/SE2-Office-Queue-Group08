class Ticket {
    waitingTime: String;
    ticketId: String;
    constructor(ticketId: String, waitingTime: String) {
        this.ticketId = ticketId;
        this.waitingTime = waitingTime;
    }
    
}

export default Ticket;