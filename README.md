# DarkS1ders File Encryption and Login System

## Overview

DarkS1ders File Encryption and Login System is a secure Java-based application that provides file encryption/decryption functionality alongside a secure login mechanism. It is designed to protect sensitive data and ensure secure access to the application using encrypted user credentials.

---

## Features

### File Encryption and Decryption
- **AES Encryption**: Encrypt and decrypt files with a user-specified key.
- **Modern UI**: Intuitive interface with support for file selection and result feedback.
- **Error Handling**: User-friendly error messages for invalid operations.

### Secure Login System
- **User Authentication**: Validates credentials stored in an encrypted database.
- **Password Encryption**: Ensures user credentials are stored and transmitted securely using `PBEWithMD5AndDES`.
- **Session Management**: Prevents unauthorized access to the encryption functionalities.

### Additional Capabilities
- **Custom Styles**: Modern UI styling using `styles.css`.
- **About Dialog**: Provides version information and credits.

---

## Technologies Used

- **JavaFX**: For the graphical user interface (GUI).
- **JDBC (Java Database Connectivity)**: For database interaction with Derby.
- **Cryptography**: AES (Advanced Encryption Standard) for file security and PBEWithMD5AndDES for credential encryption.
- **Maven**: For project dependency management.
- **Derby Embedded Database**: Lightweight, local database for user management.

## Usage Instructions

### Login
1. Enter your **username** and **password** in the respective fields.
2. Click **Login**. Upon successful authentication, the file encryption interface will load.

### File Encryption
1. Select a file to encrypt using the **Browse** button.
2. Enter an encryption key in the **Encryption Key** field.
3. Click **Encrypt File** and save the encrypted output.

### File Decryption
1. Select an encrypted file using the **Browse** button.
2. Enter the same key used during encryption in the **Encryption Key** field.
3. Click **Decrypt File** and save the decrypted output.
