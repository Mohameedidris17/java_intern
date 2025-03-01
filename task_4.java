import org.apache.mahout.cf.taste.impl.model.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.recommendation.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommendation.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNItemNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.model.UserSimilarity;
import org.apache.mahout.cf.taste.model.ItemSimilarity;
import org.apache.mahout.cf.taste.exception.TasteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExtendedRecommendationSystem {

    public static void main(String[] args) {
        try {
            DataModel model = new FileDataModel(new File("user_item_data.csv"));
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
            ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(model);
            NearestNUserNeighborhood userNeighborhood = new NearestNUserNeighborhood(10, userSimilarity, model);
            Recommender userRecommender = new GenericUserBasedRecommender(model, userNeighborhood, userSimilarity);
            NearestNItemNeighborhood itemNeighborhood = new NearestNItemNeighborhood(10, itemSimilarity, model);
            Recommender itemRecommender = new GenericItemBasedRecommender(model, itemNeighborhood, itemSimilarity);

            for (int userId = 1; userId <= 3; userId++) {
                System.out.println("\nRecommendations for User " + userId + " (User-Based):");
                List<RecommendedItem> userRecommendations = userRecommender.recommend(userId, 5);
                for (RecommendedItem item : userRecommendations) {
                    System.out.println("Item ID: " + item.getItemID() + ", Value: " + item.getValue());
                }

                System.out.println("\nRecommendations for User " + userId + " (Item-Based):");
                List<RecommendedItem> itemRecommendations = itemRecommender.recommend(userId, 5);
                for (RecommendedItem item : itemRecommendations) {
                    System.out.println("Item ID: " + item.getItemID() + ", Value: " + item.getValue());
                }
            }

            int itemId = 103;
            System.out.println("\nRecommendations for Item " + itemId + " (User-Based):");
            for (int userId = 1; userId <= 3; userId++) {
                List<RecommendedItem> itemRecommendationsForUser = userRecommender.recommend(userId, 5);
                for (RecommendedItem item : itemRecommendationsForUser) {
                    if (item.getItemID() == itemId) {
                        System.out.println("User " + userId + " might like Item " + itemId + " with a predicted value of " + item.getValue());
                    }
                }
            }

            System.out.println("\nItems similar to Item " + itemId + ":");
            for (int i = 1; i <= model.getNumItems(); i++) {
                double similarityValue = itemSimilarity.itemSimilarity(itemId, i);
                if (similarityValue > 0) {
                    System.out.println("Item " + i + " has similarity score " + similarityValue);
                }
            }

        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
