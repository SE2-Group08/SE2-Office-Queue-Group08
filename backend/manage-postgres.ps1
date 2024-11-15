param (
    [string]$Action
)

# Function to start the database
function Start-Database {
    Write-Host "Starting PostgreSQL container..."
    docker-compose up -d
    Write-Host "PostgreSQL container started."
}

# Function to stop the database
function Stop-Database {
    Write-Host "Stopping PostgreSQL container..."
    docker-compose down
    Write-Host "PostgreSQL container stopped."
}

# Function to restart the database
function Restart-Database {
    Write-Host "Restarting PostgreSQL container..."
    docker-compose down
    docker-compose up -d
    Write-Host "PostgreSQL container restarted."
}

# Function to display the help menu
function Show-Help {
    Write-Host "Usage: ./manage-postgres.ps1 [start|stop|restart]"
    Write-Host "Commands:"
    Write-Host "  start   - Start the PostgreSQL container"
    Write-Host "  stop    - Stop the PostgreSQL container"
    Write-Host "  restart - Restart the PostgreSQL container"
}

# Main script logic
switch ($Action) {
    "start" {
        Start-Database
    }
    "stop" {
        Stop-Database
    }
    "restart" {
        Restart-Database
    }
    default {
        Show-Help
    }
}
