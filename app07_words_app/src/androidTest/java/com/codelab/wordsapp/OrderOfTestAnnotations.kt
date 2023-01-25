package com.codelab.wordsapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderOfTestAnnotations {

	@Before
	fun beforeTest() {
		println("beforeTest")
	}

	@After
	fun afterTest() {
		println("afterTest")
	}

	@Test
	fun test_a() {
		println("Test A")
	}

	@Test
	fun test_b() {
		println("Test B")
	}

	@Test
	fun test_c() {
		println("Test C")
	}

	companion object {
		@BeforeClass
		@JvmStatic
		fun beforeClassTest() {
			println("Test Class Initial")
		}

		@AfterClass
		@JvmStatic
		fun afterClassTest() {
			println("Test Class Finalization")
		}
	}
}