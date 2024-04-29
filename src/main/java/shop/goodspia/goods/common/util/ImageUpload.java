package shop.goodspia.goods.common.util;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUpload {
    /**
     * 이미지 업로드
     * @param multipartFile 이미지 파일
     * @return 저장된 이미지 경로
     */
    String uploadImage(MultipartFile multipartFile);
}
