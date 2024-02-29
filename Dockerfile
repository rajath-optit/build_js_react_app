# Use an official Node.js runtime as a parent image
FROM node:14-alpine

# Set the working directory to /app
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install app dependencies
RUN npm install

# Copy the current directory contents to the container at /app
COPY . .

# Expose port 8080
EXPOSE 8080

# Define environment variable
ENV REACT_APP_API_URL=http://api.example.com

# Run npm start when the container launches
CMD ["npm", "start"]
