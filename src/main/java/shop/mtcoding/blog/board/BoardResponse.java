package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;


public class BoardResponse {
    // 일단은 V2 말고 이거부터 이해
    // 1. 리턴을 못하니까 2. 화면에 필요한것만 전달 3. 화면에 안보여도 pk 전달을 위해
    @Data
    public static class DetailDTO {
        private Integer boardId;
        private String title;
        private String content;
        private Boolean isOwner;
        private Integer userId;
        private String username;

        public DetailDTO(Board board, User sessionUser) {
            this.boardId = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = false;

            if (board.getUser().getId() == sessionUser.getId()) {
                isOwner = true;
            }
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
        }
    }

    @Data
    public static class DetailDTOV2 {
        private Integer id;
        private String title;
        private String content;
        private Boolean isOwner;
        private UserDTO user;

        public DetailDTOV2(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = false;

            if (board.getUser().getId() == sessionUser.getId()) {
                isOwner = true;
            }
            this.user = new UserDTO(board.getUser());
        }

        @Data
        public class UserDTO {
            private Integer id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

    }

}
