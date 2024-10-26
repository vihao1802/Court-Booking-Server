<h1 align="center">Court Booking Website</h1>
<p align="center">
  <a href="https://github.com/vihao1802/Court-Booking-Server/blob/main/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-yellow.svg" target="_blank" />
  </a>
  <a href="https://github.com/vihao1802/Court-Booking-Server/watchers">
    <img alt="GitHub" src="https://img.shields.io/github/watchers/vihao1802/Website-Classin" target="_blank" />
  </a>
</p>
<p align="center">
  <img src="https://img.shields.io/badge/React-%2320232a.svg?logo=react&logoColor=%2361DAFB"/></a>&nbsp
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?logo=mui&logoColor=white"/></a>&nbsp 
  <img src="https://img.shields.io/badge/Next.js-black?logo=next.js&logoColor=white"/></a>&nbsp
  <br>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff"/></a>&nbsp 
  <img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=fff"/></a>&nbsp
  <br>
  <img src="https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white"/></a>&nbsp 
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=000"/></a>&nbsp 
</p>

> `Court Booking Website` that provides an optimzed UI with various features in booking system such as online payment with QR code scan (ZaloPay, MoMo), booking management, etc.

<!-- <img src="/docs/screenshot.png" width="100%"> -->

<!-- ### 📄 PDF: <a href="" target="_blank">Link</a> -->
<!-- ### 📄 Slide: <a href="" target="_blank">Link</a> -->

<!-- GETTING STARTED -->

## 🎯 Getting Started

- You can view this frontend website repo at: <a href="https://github.com/vihao1802/Court-Booking-Client" target="_blank">Court Booking Client</a>

### 💎 Prerequisites
- You should create a `main` directory to wrap 2 repo (client and server) of this system
- <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" target="_blank">JDK >= 17</a>
- <a href="https://maven.apache.org/" target="_blank">Maven 3.0.0</a>

### ⚙️ Installation

1. Clone the repo in the `main` directory

```sh
git clone https://github.com/vihao1802/Court-Booking-Client.git
```
```sh
git clone https://github.com/vihao1802/Court-Booking-Server.git
```

2. Create file `application.properties` in folder `src/main/resources` with format:

```env
spring.application.name=court-booking-server
spring.datasource.url=jdbc:mysql://localhost:3306/court_booking_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.application.api-prefix=/api/v1

# ZALOPAY ( Learn more about ZaloPay Developer at: https://docs.zalopay.vn/v2/general/overview.html )
zalopay.app_id=2553
zalopay.key1=PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL
zalopay.key2=kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz
zalopay.endpoint=https://sb-openapi.zalopay.vn/v2/create
zalopay.redirectUrl=
zalopay.callbackUrl=

# JWT
jwt.secretSigningKey=
jwt.expirationTime=
```

## 🚀 Usage

### Client ( Run in termnial ):

- From `main` directory

```
cd client
```

- Run `client`

```
npm run dev
```

### Server:

- Run `CourtBookingServerApplication` file in `src/main/java/com/court_booking_project/court_booking_server`

## ✨ Code Contributors

- This project exists thanks to all the people who contribute.
<a href="https://github.com/vihao1802/Court-Booking-Server/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=vihao1802/Court-Booking-Server" />
</a>

Made with [contrib.rocks](https://contrib.rocks).

## 📷 Screenshots

- Go to this: <a href="" target="_blank">SCREENSHOT.md</a>

## 📝 License

Copyright © 2024 [Tran Vi Hao](https://github.com/vihao1802).<br />
This project is [MIT](https://github.com/vihao1802/Court-Booking-Server/blob/main/LICENSE) licensed.
