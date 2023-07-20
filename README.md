# SE114.N21_Nhom14
Đồ án Nhập môn ứng dụng di động của nhóm 14

## Hướng dẫn cài đặt
Bước 1. Download file .apk tại đường dẫn https://drive.google.com/file/d/1wYTA9S1d-gQy0wqcN7Ei0nDD7tQAoon4/view?usp=sharing
Bước 2. Sau khi tải xuống hoàn thành, nhấn chọn trực tiếp vào file cài đặt .apk, lưu ý cấp quyền cài đặt nếu có yêu cầu
Bước 3. Cài đặt hoàn thành, nhấn vào icon trên danh sách ứng dụng để sử dụng

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
#### Home Fragment
Hiển thị thanh tìm kiếm (bài học, lớp học, tên giảng viên), nút thông báo và danh sách các bài học gần nhất
#### Courses Fragment
Hiển thị thanh tìm kiếm, nút thông báo và danh sách lớp học đang học (đối với học viên) hoặc danh sách lớp đang quản lý (đối với giảng viên)
#### Assignments Fragment
Hiển thị danh sách bài tập, được phân loại theo 2 TAB gồm Sắp đến hạn nhưng chưa hoàn thành và Đã hết hạn.
TAB Sắp hết hạn sẽ hiển thị chữ đỏ
TAB Đã hết hạn sẽ hiển thị trạng thái đã làm bài hay chưa và số điểm sau khi được hệ thống hoặc giảng viên chấm điểm.
#### Assign Fragment
Hiển thị các bài tập đã tạo và nút "Tạo bài tập" (chỉ dành cho đối tượng là giảng viên)
#### Account Fragment
Hiển thị thẻ thông tin người dùng (nhấn vào để chuyển sang trang cập nhật thông tin tài khoản), nút Đổi mật khẩu, nút Cài đặt hiển thị (thông báo và ngôn ngữ) và nút Đăng xuất

### Study Activity
- Hiển thị nội dung bài học
- Nút đặt câu hỏi cho giảng viên

### Do Assignment Activity
- Hiển thị nội dung bài kiểm tra và ghi nhận bài làm (đối với học viên)
- Tạo trình soạn bài kiểm tra hoặc nhập bài kiểm tra (đối với giảng viên)
(Chưa hoàn thiện)
