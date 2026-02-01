-- Tạo database
CREATE DATABASE IF NOT EXISTS bookstore;
USE bookstore;

-- Tạo bảng category
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Tạo bảng book
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    price DOUBLE,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);

-- Thêm dữ liệu mẫu cho category
INSERT INTO category (name) VALUES 
('Công nghệ phần mềm'),
('Hệ thống thông tin'),
('An toàn thông tin'),
('Mạng máy tính'),
('Khoa học dữ liệu');

-- Thêm dữ liệu mẫu cho book
INSERT INTO book (title, author, price, category_id) VALUES 
('Phát triển phần mềm mã nguồn mở', 'Nguyễn Đình Anh', 12.0, 1),
('Cơ sở dữ liệu phân tán', 'Cao Tùng Anh', 10.0, 2),
('Điều tra tấn công', 'Văn Thiện Hoàng', 11.0, 3),
('Mạng máy tính nâng cao', 'Hàn Minh Châu', 13.0, 4),
('Phân tích dữ liệu truyền thống và hiện đại', 'Nguyễn Thị Hải Bình', 12.0, 5),
('Hệ Quản trị Oracle', 'Võ Hoàng Khang', 1.0, 1);
