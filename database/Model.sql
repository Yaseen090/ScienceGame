-- Table for storing user signup information
CREATE TABLE IF NOT EXISTS Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    DOB DATE NOT NULL
);

-- Table for storing game scores
CREATE TABLE IF NOT EXISTS Scores (
    ScoreID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Score INT NOT NULL,
    GameMode ENUM('PlayerVsPlayer', 'PlayerVsComputer', 'TimedSinglePlayer') NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
