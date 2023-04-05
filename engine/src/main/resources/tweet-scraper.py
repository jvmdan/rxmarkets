import time
from datetime import datetime, timedelta, date
import json
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select

def get_tweets(hashtag, num_tweets=50, driver_path='/Users/samgain/Downloads/chromedriver'):
    #url = f'https://twitter.com/hashtag/{hashtag}'
    service = Service(executable_path=driver_path)
    driver = webdriver.Chrome(service=service)
    #driver.get(url)

    driver.get("https://twitter.com/search-advanced")

    # Wait for the hashtag input field to be visible
    WebDriverWait(driver, 20).until(EC.visibility_of_element_located((By.XPATH, '//input[@name="theseHashtags"]')))

    # Input the hashtag into the search field
    hashtag_field = driver.find_element(By.XPATH, '//input[@name="theseHashtags"]')
    hashtag_field.send_keys(hashtag)

    # Set the date range for the last 24 hours
    since_date = datetime.now() - timedelta(days=1)
    until_date = datetime.now()

    # Find the dropdowns
    from_month_selector = Select(driver.find_element(By.ID, 'SELECTOR_2'))
    from_day_selector = Select(driver.find_element(By.ID, 'SELECTOR_3'))
    from_year_selector = Select(driver.find_element(By.ID, 'SELECTOR_4'))

    to_month_selector = Select(driver.find_element(By.ID, 'SELECTOR_5'))
    to_day_selector = Select(driver.find_element(By.ID, 'SELECTOR_6'))
    to_year_selector = Select(driver.find_element(By.ID, 'SELECTOR_7'))

    # Select desired date range
    from_month_selector.select_by_value(str(since_date.month))
    from_day_selector.select_by_value(str(since_date.day))
    from_year_selector.select_by_value(str(since_date.year))

    to_month_selector.select_by_value(str(until_date.month))
    to_day_selector.select_by_value(str(until_date.day))
    to_year_selector.select_by_value(str(until_date.year))

    # Find the search button and click on it
    search_button_xpath = '//div[@role="button" and @tabindex="0"]//span[contains(text(), "Search")]'
    search_button = driver.find_element(By.XPATH, search_button_xpath)
    search_button.click()

    WebDriverWait(driver, 10).until(EC.visibility_of_all_elements_located((By.XPATH, '//div[@data-testid="tweetText"]')))

    soup = BeautifulSoup(driver.page_source, 'lxml')

    tweets = []
    max_scroll_attempts = 50
    scroll_attempts = 0

    while len(tweets) < num_tweets and scroll_attempts < max_scroll_attempts:
        try:
            WebDriverWait(driver, 5).until(EC.visibility_of_all_elements_located((By.XPATH, '//div[@data-testid="tweetText"]')))

            soup = BeautifulSoup(driver.page_source, 'lxml')

            for tag in soup.find_all(attrs={"data-testid": "tweetText"}):
                tweet = {}
                tweet['data'] = ' '.join(tag.stripped_strings)
                if tweet not in tweets:
                    tweets.append(tweet)

                if len(tweets) >= num_tweets:
                    break

            # Scroll down the page
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        #  time.sleep(2)

        except Exception as e:
            print(f"Exception encountered: {e}")
            scroll_attempts += 1
            time.sleep(2)

            # Dismiss the popup
            try:
                close_button_xpath = '//div[@data-testid="app-bar-close"]'
                close_button = driver.find_element(By.XPATH, close_button_xpath)
                close_button.click()
            except Exception as close_exception:
                print(f"Close button exception: {close_exception}")

    driver.quit()
    return tweets

def save_tweets(tweets, filename):
    # Save prettified JSON to file
    with open(filename, 'w') as file:
        json.dump(tweets, file, indent=4)


if __name__ == '__main__':
    hashtag = 'apple'
    # Set the filename to today's date
    today = date.today()
    filename = f'tweets_{today}.json'
    tweets = get_tweets(hashtag)
    save_tweets(tweets, filename)
