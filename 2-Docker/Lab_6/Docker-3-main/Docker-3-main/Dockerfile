#Uses a lightweight Python base image
FROM python:3.11-slim

#Sets the working directory inside the container
WORKDIR /app

#Copies the application source code into the container
COPY . .

#Installs Flask inside the container
RUN pip install flask

#Sets default environment variables
#These values apply if no overrides are provided at runtime
ENV APP_MODE=production
ENV APP_REGION=canada-west

#Documents the application listening port
EXPOSE 8080

#Runs the Flask application when the container starts
CMD ["python", "app.py"]