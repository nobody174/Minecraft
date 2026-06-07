import { version, getVersion } from "./index";

describe("Minecraft Addons Tools", () => {
  test("should export version constant", () => {
    expect(version).toBe("1.0.0");
  });

  test("getVersion should return version string", () => {
    expect(getVersion()).toBe("1.0.0");
  });
});
