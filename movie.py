import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

ratings_path = "Documents/ratings.csv"
movies_path = "Documents/movies.csv"

ratings_df = pd.read_csv(ratings_path)
movies_df = pd.read_csv(movies_path)

df = ratings_df[['userId', 'movieId', 'rating']]

user_movie_matrix = df.pivot(index='userId', columns='movieId', values='rating')
user_movie_matrix.fillna(0, inplace=True)

user_similarity = cosine_similarity(user_movie_matrix)
user_similarity_df = pd.DataFrame(user_similarity, index=user_movie_matrix.index, columns=user_movie_matrix.index)

def get_top_n_similar_users(user_id, user_similarity_df, n=5):
    similar_users = user_similarity_df[user_id].sort_values(ascending=False).index[1:n+1]
    return similar_users

def recommend_movies(user_id, user_movie_matrix, user_similarity_df, movies_df, n_recommendations=5):
    similar_users = get_top_n_similar_users(user_id, user_similarity_df)
    similar_users_ratings = user_movie_matrix.loc[similar_users]

    movie_recommendations = similar_users_ratings.apply(lambda row: np.dot(row, user_similarity_df.loc[user_id][similar_users]) / np.sum(user_similarity_df.loc[user_id][similar_users]), axis=0)
    user_rated_movies = user_movie_matrix.loc[user_id] > 0
    movie_recommendations = movie_recommendations[~user_rated_movies]

    top_n_recommendations = movie_recommendations.sort_values(ascending=False).head(n_recommendations).index
    top_n_recommendations_df = movies_df[movies_df['movieId'].isin(top_n_recommendations)].reset_index(drop=True)

    return top_n_recommendations_df[['movieId', 'title']]

user_id = 44
recommendations = recommend_movies(user_id, user_movie_matrix, user_similarity_df, movies_df)

# Save recommendations to CSV
recommendations.to_csv(f'recommended_movies_user_{user_id}.csv', index=False)
print(f"Recommended movies for user {user_id} saved to 'recommended_movies_user_{user_id}.csv'")
