# UDP
UDP (User Datagram Protocol) là một trong hai giao thức cơ bản trong mô hình TCP/IP (cùng với giao thức TCP). UDP là một giao thức truyền dữ liệu không đồng bộ, không đảm bảo tính toàn vẹn dữ liệu, không đảm bảo thứ tự truyền và không đảm bảo tính sẵn sàng.

UDP được sử dụng rộng rãi trong các ứng dụng cần truyền dữ liệu nhanh và không quan trọng tính toàn vẹn của dữ liệu, chẳng hạn như trò chơi trực tuyến, video trực tuyến, âm thanh trực tuyến, các ứng dụng IoT (Internet of Things)...

Một điểm mạnh của UDP là tốc độ truyền dữ liệu nhanh hơn so với TCP, do không có quá trình xác thực và phục hồi dữ liệu như TCP. Tuy nhiên, điều này cũng đồng nghĩa với việc dữ liệu có thể bị mất hoặc bị trùng lặp mà không được phát hiện. Vì vậy, khi sử dụng UDP, các ứng dụng cần phải thiết kế các cơ chế xử lý lỗi và đồng bộ dữ liệu riêng để đảm bảo tính toàn vẹn của dữ liệu.
