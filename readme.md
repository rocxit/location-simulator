# Getting Started

## How start
./gradlew bootRun

## API
### To check status of the API (Health check)
GET: http://localhost:8080/assessment/status

### Coordinate finder
GET: http://localhost:8080/assessment/coordinates?origin={originValue}&destination=${destinationValue}

originValue and destinationValue can be human readable address or can be in latitude,longitude(no space after , in latitude and longitude) format

example: http://localhost:8080/assessment/coordinates?origin=20.70331,81.55460&destination=21.25532,81.62944

or it address also be in human readable format

ex. http://localhost:8080/assessment/coordinates?origin=dhamtari&destination=raipur

The response will be in Array of strings, string will be csv of latitude and longitude

Added logic for points in interval, but it's totally restricted to how many coordinates google returns bwtween two points
