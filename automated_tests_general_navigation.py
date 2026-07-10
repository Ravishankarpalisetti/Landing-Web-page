import pytest
from playwright.sync_api import sync_playwright, expect

@pytest.fixture(scope="function")
def page():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()
        yield page
        context.close()
        browser.close()

class TestGeneralNavigation:
    def test_verify_clicking_on_the_main_image_on_the_homepage(self, page):
        # Step 1: Navigate to https://dilipbuildcon.com/
        page.goto("https://dilipbuildcon.com/")
        
        # Verify the application home page loads successfully
        expect(page).to_have_url("https://dilipbuildcon.com/")
        
        # Step 2: Verify image is visible and clickable
        image_selector = "div.e-active:nth-of-type(1) > div.elementor-element > div.e-con-inner > div.elementor-element:nth-of-type(4) > div.elementor-widget-container > div.swiper:nth-of-type(1) > div.swiper-wrapper > div.elementor:nth-of-type(3) > article.elementor-element > div.e-con-inner > div.elementor-element:nth-of-type(1) > div.e-con-inner > div.elementor-element > div.elementor-widget-container > img.attachment-full"
        image_locator = page.locator(image_selector)
        
        expect(image_locator).to_be_visible()
        expect(image_locator).to_be_enabled()
        
        # Step 3: Click on the image
        image_locator.click()
        
        # Expected Result: The page reloads or navigates internally to https://dilipbuildcon.com/
        expect(page).to_have_url("https://dilipbuildcon.com/")

if __name__ == "__main__":
    from dotenv import load_dotenv
    load_dotenv()
    import pytest
    pytest.main([__file__, "-v", "--tb=short"])