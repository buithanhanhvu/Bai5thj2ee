# Bookstore - Quản lý Sách & Giỏ hàng

## Mô tả
Ứng dụng Spring Boot quản lý sách, danh mục và giỏ hàng. Hỗ trợ CRUD sách, tìm kiếm theo từ khóa và checkout lưu thông tin giỏ hàng vào cơ sở dữ liệu MySQL.

## Công nghệ
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA + Hibernate
- MySQL
- Thymeleaf
- Lombok
- Bootstrap 5

## Tính năng chính
- Thêm, sửa, xóa, hiển thị danh sách sách
- Tìm kiếm sách theo tiêu đề, tác giả, danh mục
- Giỏ hàng: thêm/xóa/cập nhật số lượng, tính tổng
- Checkout: lưu hóa đơn và chi tiết hóa đơn
- Validation dữ liệu khi lưu sách và danh mục

## Cấu trúc thư mục
```
Buithanhanhvu/
├── src/
│   ├── main/
│   │   ├── java/nhom6/Buithanhanhvu/
│   │   │   ├── controllers/
│   │   │   ├── daos/
│   │   │   ├── entities/
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── BuithanhanhvuApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
├── pom.xml
└── database.sql
```

## Cấu hình & chạy dự án
### 1) Tạo database
Import file database.sql (phpMyAdmin/MySQL Workbench).

### 2) Cấu hình kết nối
Mở src/main/resources/application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=
server.port=8081
```

### 3) Build & Run
```
./mvnw.cmd -DskipTests package
./mvnw.cmd spring-boot:run
```

### 4) Truy cập
- Trang chủ: http://localhost:8081/
- Danh sách sách: http://localhost:8081/books
- Giỏ hàng: http://localhost:8081/books/cart

## API chính
- GET /books — danh sách sách
- GET /books/search?keyword=... — tìm kiếm
- GET /books/add — form thêm sách
- POST /books/add — lưu sách
- GET /books/edit/{id} — form sửa
- POST /books/edit — cập nhật
- GET /books/delete/{id} — xóa
- POST /books/add-to-cart — thêm vào giỏ
- GET /books/cart — xem giỏ hàng
- POST /books/update-cart — cập nhật số lượng
- POST /books/remove-from-cart — xóa khỏi giỏ
- POST /books/clear-cart — xóa giỏ hàng
- POST /books/checkout — checkout

## Lưu ý
- Ứng dụng chạy cổng 8081.
- Đảm bảo MySQL đang chạy trước khi start app.

## Tác giả
- Nhóm 6 - Dự án Bài tập lớn

## License
Dự án học tập - HUTECH University

---
**Lưu ý:** Dự án đã được sửa lỗi hoàn chỉnh và sẵn sàng chạy! 🚀
