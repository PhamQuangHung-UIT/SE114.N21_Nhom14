# SE114.N21_Nhom14
Đồ án Nhập môn ứng dụng di động của nhóm 14

## Các tính năng của ứng dụng
1. Xem bài giảng
2. Đặt câu hỏi cho giảng viên

### Các chức năng riêng dành cho giảng viên
1. Tạo lớp học, thêm học viên
2. Đăng bài giảng & bài tập
3. Giải đáp thắc mắc của học viên

## Activity
### Splash Activity
- Hiển thị logo, slogan trước khi đến trang đăng nhập / trang chủ (nếu ghi nhớ đăng nhập)

### Login Activity
- Nhập username và password để đăng nhập vào ứng dụng
- Người dùng có thể chuyển sang trang đăng ký từ đây

### Sign Up Activity
- Nhập username và nhập 2 lần password để đăng ký
- Các thông tin khác (avatar, họ tên, ngày sinh, email, ...) người dùng sẽ cập nhật sau

### Main Activity
- Chứa 4 fragment có thể chuyển qua lại, bao gồm: trang chủ, lớp học, bài tập, cài đặt tài khoản
- Hầu hết sử dụng RecyclerView để hiển thị các items và các Adapter để mount data và xử lí nút nhấn bằng các Interface

### Study Activity
- Hiển thị nội dung bài học

### Exam Activity
- Hiển thị nội dung bài kiểm tra và ghi nhận bài làm (đối với học viên)
- Tạo trình soạn bài kiểm tra hoặc nhập bài kiểm tra (đối với giảng viên)
(Chưa hoàn thiện)
