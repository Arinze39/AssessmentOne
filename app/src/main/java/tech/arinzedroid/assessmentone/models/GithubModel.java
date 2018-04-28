package tech.arinzedroid.assessmentone.models;

/**
 * Created by ACER on 4/27/2018.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class GithubModel {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public GithubModel() {
    }

    /**
     *
     * @param items
     * @param totalCount
     * @param incompleteResults
     */
    public GithubModel(Integer totalCount, Boolean incompleteResults, ArrayList<Item> items) {
        super();
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Parcel
    public static class Item {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("html_url")
        @Expose
        private String htmlUrl;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("homepage")
        @Expose
        private String homepage;
        @SerializedName("stargazers_count")
        @Expose
        private Integer stargazersCount;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("forks")
        @Expose
        private Integer forks;

        /**
         * No args constructor for use in serialization
         *
         */
        public Item() {
        }

        /**
         *
         * @param updatedAt
         * @param forks
         * @param htmlUrl
         * @param createdAt
         * @param description
         * @param name
         * @param owner
         * @param language
         * @param fullName
         * @param stargazersCount
         * @param homepage
         */
        public Item(String name, String fullName, Owner owner, String htmlUrl, String description, String createdAt, String updatedAt, String homepage, Integer stargazersCount, String language, Integer forks) {
            super();
            this.name = name;
            this.fullName = fullName;
            this.owner = owner;
            this.htmlUrl = htmlUrl;
            this.description = description;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.homepage = homepage;
            this.stargazersCount = stargazersCount;
            this.language = language;
            this.forks = forks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public Integer getStargazersCount() {
            return stargazersCount;
        }

        public void setStargazersCount(Integer stargazersCount) {
            this.stargazersCount = stargazersCount;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Integer getForks() {
            return forks;
        }

        public void setForks(Integer forks) {
            this.forks = forks;
        }

        @Parcel
        public static class Owner {

            @SerializedName("login")
            @Expose
            private String login;
            @SerializedName("avatar_url")
            @Expose
            private String avatarUrl;
            @SerializedName("type")
            @Expose
            private String type;

            /**
             * No args constructor for use in serialization
             *
             */
            public Owner() {
            }

            /**
             *
             * @param avatarUrl
             * @param login
             * @param type
             */
            public Owner(String login, String avatarUrl, String type) {
                super();
                this.login = login;
                this.avatarUrl = avatarUrl;
                this.type = type;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }

    }

}