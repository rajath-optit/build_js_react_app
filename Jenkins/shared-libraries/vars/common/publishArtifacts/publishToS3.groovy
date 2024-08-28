package 'org.common.build.Publish-Artifacts.PublishToS3'

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest

def publishToS3(String bucketName, String filePath) {
    def s3 = AmazonS3ClientBuilder.defaultClient()

    echo "Publishing artifact to S3 bucket: ${bucketName}..."

    try {
        PutObjectRequest request = new PutObjectRequest(bucketName, filePath, new File(filePath))
        s3.putObject(request)
        echo "Artifact published successfully to S3 bucket: ${bucketName}"
    } catch (Exception e) {
        echo "Failed to publish artifact to S3 bucket: ${bucketName}"
        error(e.toString())
    }
}

return this
