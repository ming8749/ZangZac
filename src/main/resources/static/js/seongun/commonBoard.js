document.addEventListener('DOMContentLoaded', function() {
            // 스크롤이 끝으로 이동 시 이벤트 발생
            window.addEventListener('scroll', function() {
                if (window.pageYOffset <= 0) {
                    document.getElementById('back-to-top').classList.remove('show');
                } else {
                    document.getElementById('back-to-top').classList.add('show');
                }
            });

            window.addEventListener('touchmove', function() {
                if (window.pageYOffset <= 0) {
                    document.getElementById('back-to-top').classList.remove('show');
                } else {
                    document.getElementById('back-to-top').classList.add('show');
                }
            });

            document.getElementById('back-to-top').addEventListener('click', function() {
                // 페이지 맨 위로 스크롤하는 애니메이션
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                });
                document.getElementById('back-to-top').classList.remove('show');
            });
        });