package com.udemy.section4;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 레이턴시(지연시간)을 최적화하는 방법의 예시<br>
 * 하나의 이미지를 할당하고자 하는 스레드의 수만큼 분리한 후<br>
 * 각각의 스레드가 병렬적으로 이미지를 처리하도록 만든다.<br>
 */
public class LatencyOptimization {
	public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
	public static final String DESTINATION_FILE = "./many-flowers.jpg";

	public static void main(String[] args) throws IOException {
		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
			BufferedImage.TYPE_INT_BGR);

		long startTime = System.currentTimeMillis();
		// recolorSingleThreaded(originalImage, resultImage);
		recolorMultiThreaded(originalImage, resultImage, 2);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

		File outputFile = new File((DESTINATION_FILE));
		ImageIO.write(resultImage, "jpg", outputFile);
		System.out.println(duration + "ms");
	}

	public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage,
		int numberOfThreads) {
		List<Thread> threads = new ArrayList<>();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight() / numberOfThreads;

		for (int i = 0; i < numberOfThreads; i++) {
			final int threadMultiplier = i;
			Thread thread = new Thread(() -> {
				int leftCorner = 0;
				int topCorner = height * threadMultiplier;

				recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
			});

			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}
	}

	public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
		recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
	}

	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner,
		int topCorner, int width, int height) {
		for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
			for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
				recolorPixel(originalImage, resultImage, x, y);
			}
		}
	}

	public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		int rgb = originalImage.getRGB(x, y);

		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);

		int newRed;
		int newGreen;
		int newBlue;
		if (isShadeOfGray(red, green, blue)) {
			newRed = Math.min(255, red + 10);
			newGreen = Math.max(0, green - 80);
			newBlue = Math.max(0, blue - 20);
		} else {
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}

		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		setRGB(resultImage, x, y, newRGB);
	}

	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		WritableRaster imageRaster = image.getRaster();
		imageRaster.setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}

	public static boolean isShadeOfGray(int red, int green, int blue) {
		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 & Math.abs(green - blue) < 30;
	}

	/**
	 * 색상 컴포넌트 추출 작업의 반대 작업을 수행하고 있다.<br>
	 * 각 컴포넌트를 가져다가 ARGB 픽셀 표현에서 해당되는 위치로 시프트하는 작업이다.<br>
	 * 추출 작업과 반대로 비트 단위 OR 처리해주면 된다.
	 */
	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;
		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;
		rgb |= 0xFF000000;

		return rgb;
	}

	/**
	 * 특정 컴포넌트(빨강, 초록, 파랑)을 추출하려면,<br>
	 * 해당 픽셀에서 원하는 컴포넌트만 남기고<br>
	 * 다른 색상 컴포넌트는 모두 제거해야 한다.<br>
	 * 이를 위해 비트 마스크를 적용한다.<br>
	 * <p>
	 * 비트마스크는 어떤 비트를 남기고,<br>
	 * 어떤 비트를 제거할지를 정의하는 역할을 한다.<br>
	 * 모든 X에 대해, X AND 0 = 0이 성립하기 때문에<br>
	 * 0x00(2진법으로 0000 0000)을 사용하면 컴포넌트를 제거할 수 있다.<br>
	 * 반대로 0x00(2진법으로 0000 0000)을 사용하면 컴포넌트를 제거할 수 있다.<br>
	 * <p>
	 * 이후 컴포넌트를 나타내는 바이트를 가장 낮은 바이트로 시프트해야한다.<br>
	 * 예를 들어 getRed(..) 메서드의 경우,<br>
	 * 0x76543210에 비트마스크를 적용하게 되면 0x00540000이라는 결과를 얻게 되지만,<br>
	 * 정작 필요한 값은 0x00000054이다.<br>
	 * 따라서 비트마스크의 결과값에 있는 모든 비트를<br>
	 * '>>' 연산자를 이용해 오른쪽으로 시프트해야 한다.<br>
	 */
	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}

	public static int getGreen(int rgb) {

		return (rgb & 0x0000FF00) >> 8;
	}

	public static int getBlue(int rgb) {
		return rgb & 0x000000FF;
	}
}
